package effect.rotation

import zio.{IO, ZIO}

object EffectRotation {
  sealed trait MyError
  case class Missing(ingredient: Ingredient) extends MyError
  case class NotEnough(ingredient: Ingredient, missing: Int) extends MyError
  case class InvalidRecipe(recipe: Recipe) extends MyError
  case object CannotBake extends MyError

  case class Ingredient(name: String)
  case class Recipe(name: String, ingredients: Map[Ingredient, Int], instructions: Iterable[String])
  type Cake = String
  var dbFridge = Map(
    Ingredient("Flour") -> 30,
    Ingredient("Milk") -> 20,
    Ingredient("Egg") -> 6,
    Ingredient("Butter") -> 50,
    Ingredient("Baking powder") -> 20,
    Ingredient("Salt") -> 10,
  )

  def takeIngredient(ingredient: Ingredient, quantity: Int): IO[MyError, Unit] = ZIO.succeed {
    dbFridge.get(ingredient)
      .toRight(Missing(ingredient))
      .flatMap { availableQuantity =>
        if (availableQuantity < quantity) Left(NotEnough(ingredient, quantity - availableQuantity))
        else { // Remove from fridge
          dbFridge = dbFridge.updated(ingredient, availableQuantity - quantity)
          Right(())
        }
      }
  }

  def takeIngredients(ingredients: List[(Ingredient, Int)]): IO[MyError, Unit] = {
    ZIO.foreach(ingredients) { case (ingredient, quantity) => takeIngredient(ingredient, quantity) }
      .unit
  }

  def hasEverythingToCook(recipe: Map[Ingredient, Int]): IO[Nothing, Boolean] = ZIO.succeed {
    recipe.forall { case (ingredient, requiredQuantity) =>
      dbFridge.getOrElse(ingredient, 0) >= requiredQuantity
    }
  }

  def bake(recipe: Recipe): Either[MyError, Cake] = {
    if (recipe.instructions.exists(_.isEmpty)) Left(InvalidRecipe(recipe))
    else {
      recipe.instructions.foreach(println)
      Right(recipe.name)
    }
  }

  // Composing different functions with Either and/or IO
  def cook(recipe: Recipe): IO[MyError, Cake] =
    for {
      // From F[...]: use as-is because error type is included in ZIO type but discarded (Nothing => cannot fail)
      ready <- hasEverythingToCook(recipe.ingredients)
      _ <- ZIO.when(!ready)(ZIO.fail(CannotBake))
      // From F[Either[..., ...]]: use as-is because error type is included in ZIO type
      _ <- takeIngredients(recipe.ingredients.toList)
      // From Either[..., ...]: lift an Either to ZIO, putting Either error into ZIO error
      cake <- ZIO.fromEither(bake(recipe))
    } yield cake
}
