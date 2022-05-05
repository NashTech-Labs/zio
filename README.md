# Wrap Side-Effects as Pure Values Using IO Monad

- This template demonstrates how we can wrap the side-effects as pure values by using the ```IO``` Monad.
- In Scala, a side-effect is an expression which mutates data outside its local environment or interacts with the external world(i.e. printing to the console, making a DB call or a file read/write operation).
- These side-effects are eagerly evaluated which means the runtime evaluates them immediately during execution, without giving us any chance to stop that from happening or manipulating it.
- This eager evaluation of side-effects violates the referential transparency within our code. So we need something that gives us the ability to control WHEN these side-effects get evaluated. Luckily, we have the ```IO``` Monad.
- The ```IO``` monad is like a container/wrapper that contains/wraps a description of a side effectful computation.
- It provides us the ability to evaluate the side-effects when we need to i.e. lazy evaluation.
- The type ```IO``` was first introduced in Haskell and used in other functional languages using libraries. 
- In this template, we’ll be using the cats-effect library to demonstrate the use of IO Monad.

The example we will use throughout this template is a number guessing game. When the program runs it will ask the user for his/her name and on input, the program will generate a random number from 1 to 10 and ask the user to guess the number. If the user guesses the number correctly, the program will inform the user about that, otherwise, the program will notify failure. Finally, once the game round is complete, the program will ask the user to continue or exit the game.

In this template, I have added two runnable applications namely:
1. [NumberGuessingGameHavingSideEffects](src/main/scala/NumberGuessingGameHavingSideEffects.scala): This is a number guessing game having many side-effects like printing to the console, reading from the console and generating random data.
2. [WrappingSideEffectsUsingIOMonad](src/main/scala/WrappingSideEffectsUsingIOMonad.scala): This is a functionally pure version of the above number guessing game. It consists of only pure functions. In this, we've removed all the side-effects of our number guessing game by wrapping them within the IO type. As our side-effecting code is now wrapped inside the IO, it cannot evaluate until and unless we tell it to do so. Cats IO provides us the method **unsafeRunSync()** to evaluate the encapsulated code. So to run this IO based application, we've used that method at the end of our code.

## Prerequisites

- Scala Build Tool(SBT), version 1.6.2
- Scala, version 2.13.8