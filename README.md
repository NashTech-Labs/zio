# Combining monads: the example of Cats and ZIO

Let's consider we have an IO monad to represent async code, and we want to add recoverable error (`Either[E, A]`)
or optionality `Option[A]`. Nowadays, two approaches are implemented by the major existing IO monads:
- Monad transformers by cats
- Effect rotation by ZIO

## Cats: monad transformers
Cats library provides different monads transformers in its MTL (Monad Transformers Library). You can find the full list in the [officiel Cats MTL documentation](https://typelevel.org/cats-mtl/).
In our case, we're interessed in error ones:
- `EitherT[F[_], E, A]`
- `OptionT[F[_], A]`

The main idea is to be able to wrap `IO[...]`, `IO[Either[..., ...]]` and `Either[..., ...]` under one common monad `EitherT[IO, ..., ...]`.
This is sometimes referred as a vertical composition as we **add an extra layer** on top of the `F[_]` monad (IO in our case).
Also, due this *stack* of types, it can lead to performance issues. If you want to know more, read [John A De Goes' article about monad transformers](https://degoes.net/articles/effects-without-transformers).

## ZIO: effect rotation
ZIO type includes the error directly as a type parameter. So, if you want to add a monadic behaviour, you need to add a new type parameter.
This can lead to scary types like `MyMonad[R, W, S, E, A]` if we want to have `R`ead monad, `W`rite monad, `S`tate monad and `E`rror monad for instance.
John A De Goes gives more details in [this article](https://degoes.net/articles/rotating-effects), where he explains how to discard one monad behaviour using Nothing/Any.

This is how `ZIO[R, E, A]` type was designed: an IO monad with read monad `R` to read the environment and error monad `E` to handle error.

In our example, we'll focus on the alias without environment `IO[E, A]` which represents the IO monad with error handling only (environment `R` is silenced here).
Notice that if you want to silence the error handling (when your code cannot fail), you need to use `Nothing` as error type.