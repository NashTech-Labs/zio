# Composing Effects with ZIO

- This template shows how we can compose multiple effects sequentially in ZIO using some easy to understand examples.
- There are several ways by which we can compose and chain out effects together in ZIO.
- In this template, we've used some of the most common ones which are ```flatMap```, ```for comprehension```, ```foreach```, ```collectAll``` and the ```zipping``` functions.

Let's quickly understand about each of these combinators:

**flatMap**: The ```flatMap``` method of ZIO composes two effects in sequence. It requires that you pass a callback, which will receive the value of the first effect, and can return a second effect that depends on this value. 

**for comprehension**: As the ```ZIO``` data type supports both flatMap and map, we can use Scala's ```for comprehensions``` to build sequential effects. Using for comprehensions, each line in the
comprehension looks similar to a statement in procedural programming.

**foreach**: The ```foreach``` operator returns a single effect that describes performing an effect for each element of a collection in sequence. It’s similar to a for loop in procedural
programming, which iterates over values, processes them in some fashion, and collects the results.

**collectAll**: The ```collectAll``` operator returns a single effect that collects the results of a whole collection of effects.

**zip**: The ```zip``` operator sequentially combines two effects into a single effect. The resulting effect succeeds with a tuple that contains the success values of both effects.

**zipLeft**: The ```zipLeft``` operator sequentially combines two effects, returning the result of the first effect.

**zipRight**: The ```zipRight``` operator sequentially combines two effects, returning the result of the second effect.

**zipWith**: The ```zipWith``` operator combines two effects sequentially, merging their two results with the specified user-defined function.

In this template, we have added examples for each of these operators. Try executing them and check out the results.
# Prerequisites

- SBT, version 1.6.2
- Scala, version 2.13.8