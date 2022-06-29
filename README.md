# zlayer-composition

<b> A ZLayer[-RIn, +E, +ROut] describes a layer of an application: every layer in an application requires some services as input RIn and produces some services as the output ROut.</b>

#### ZLayers can be composed together horizontally or vertically:
1. <b>Horizontal Composition:-</b>They can be composed together horizontally with the ++ operator. When we compose two layers horizontally, the new layer that this layer requires all the services that both of them require, also this layer produces all services that both of them produces
2. <b>Vertical Composition:- </b> If we have a layer that requires A and produces B, we can compose this layer with another layer that requires B and produces C; this composition produces a layer that requires A and produces C. The feed operator, >>>, stack them on top of each other by using vertical composition

#### Quickstart

1. <b> Clone the repositery </b>
2. <b> Compile the project </b>
  ```bash
sbt compile
```
3. <b> Test the project </b>
  ```bash
sbt test
```
4. <b> Compile the project </b>
  ```bash
sbt run
```