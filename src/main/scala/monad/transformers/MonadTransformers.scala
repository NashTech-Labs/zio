package monad.transformers

import cats.data.{EitherT, IorT, WriterT}
import cats.effect.IO
import cats.syntax.all._

object MonadTransformers {
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

  def takeIngredient(ingredient: Ingredient, quantity: Int): IO[Either[MyError, Unit]] = IO {
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

  def takeIngredients(ingredients: List[(Ingredient, Int)]): IO[Either[MyError, Unit]] = {
    ingredients
      .traverse { case (ingredient, quantity) => takeIngredient(ingredient, quantity) }
      .map(_.head)
  }

  def hasEverythingToCook(recipe: Map[Ingredient, Int]): IO[Boolean] = IO {
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
  def cook(recipe: Recipe): IO[Either[MyError, Cake]] = {
    val eitherT: EitherT[IO, MyError, String] = for {
      // From F[...]
      ready <- EitherT.right(hasEverythingToCook(recipe.ingredients))
      _ <- EitherT.fromEither[IO](Either.cond(ready, (), CannotBake))
      // From F[Either[..., ...]]
      _ <- EitherT(takeIngredients(recipe.ingredients.toList))
      // From Either[..., ...]
      cake <- EitherT.fromEither[IO](bake(recipe))
    } yield cake
    eitherT.value
  }
}
