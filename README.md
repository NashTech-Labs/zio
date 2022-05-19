<h1 align="center">ZIO Cheat Sheet</h1>
<h3 align="center">The passionate developer's from Knoldus exploring ZIO and producing this Zio cheat-sheet</h3>

<p align="left"> <img src="https://komarev.com/ghpvc/?username=knoldus&label=Profile%20views&color=0e75b6&style=flat" alt="knoldus" /> </p>

<h2>ZIO Cheat Sheet</h2>

- This is based on ZIO 1.0.0.

<h3>The ZIO Datatype</h3>
The `ZIO[R, E, A]` is a core data type around the ZIO library. It acts as a basic building block for every ZIO based
application.

<h3>Type Parameters</h3>
The `ZIO[R, E, A]` data type has three type parameters:

- R — the environment/dependency of our effect
- E — the type of errors that our effect may throw
- A — the return type of our effect

<h3>Aliases</h3>
ZIO provide some type aliases which you can use instead of the full ZIO type signature:

| **Alias**    | **Full Type Signature**    |
|--------------|----------------------------|
| `UIO[A]`     | `ZIO[Any, Nothing, A]`     |
| `URIO[R, A]` | `ZIO[R, Nothing, A]`       |
| `Task[A]`    | `ZIO[Any, Throwable, A]`   |
| `RIO[R, A]`  | `ZIO[R, Throwable, A]`     |
| `IO[E, A]`   | `ZIO[Any, E, A]`           |

<h3>Create Effects</h3>
- WIP

<h3>Compose Effects Sequentially</h3>
The ZIO library provides different operators to compose zio effects sequentially:

| **Operator**        | **Description**                                                                                                                              |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `flatMap`           | composes two effects in sequence                                                                                                             |
| `for comprehension` | composes two or more effects in sequence                                                                                                     |
| `foreach`           | returns a single effect that describes performing an effect for each element of a collection in sequence                                     |
| `collectAll`        | returns a single effect that collects the results of a whole collection of effects                                                           |
| `zip`               | sequentially combines two effects into a single effect, resulting effect succeeds with a tuple containing the success values of both effects |
| `zipLeft`           | sequentially combines two effects, returning the result of the first effect                                                                  |
| `zipRight`          | sequentially combines two effects, returning the result of the second effect                                                                 |
| `zipWith`           | combines two effects sequentially, merging their two results with the specified user-defined function                                        |


<h3 align="left">Connect with us:</h3>
<p align="left">
<a href="https://twitter.com/knolspeak" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/twitter.svg" alt="knolspeak" height="30" width="40" /></a>
<a href="https://linkedin.com/in/knoldus" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="knoldus" height="30" width="40" /></a>
</p>

<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a> <a href="https://www.scala-lang.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/scala/scala-original.svg" alt="scala" width="40" height="40"/> </a> <a href="https://zio.dev/" target="_blank" rel="noreferrer"> <img src="https://storage.googleapis.com/knoldus-images/Techhub%20website/zio.png" alt="zio" width="40" height="40"/> </a> <a href="https://techhub.knoldus.com/" target="_blank" rel="noreferrer"> <img src="https://ibb.co/2qxV8DL" alt="techhub" width="40" height="40"/> </a></p>
