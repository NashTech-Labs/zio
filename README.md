# Exception Handling in ZIO

ZIO provides number of ways to handle exceptions. In this Template, we will going to discuss about few important exception handling methods:

### Either

It works like: If the effect executed successfully then the case Right(success) will be executed otherwise case Left(failure) will handle it from getting crashed.

### CatchAll

CatchAll method will save your code from getting crashed with all types of exceptions.

### CatchSome

CatchSome method is used to handle any specific type of exception.

### OrElse

OrElse method provide another attempt with some different approach to handle to code from crash.

### RetryN

RetryN method provides specific number of attempts to retry the same approach.

## Prerequisites

| S No. | Tools | versions |
|--|--|--|
| 1. | Scala | v2.13 |
| 2. | SBT | v1.4.3 |

## Library Dependency

`"dev.zio" %% "zio" % "1.0.14"`



