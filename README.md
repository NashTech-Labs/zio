## zio-json filtering
<b> According to ZIO documentation at the core of ZIO is ZIO, a powerful effect type inspired by Haskell's IO monad. This data type lets you solve complex problems with simple, type-safe, testable, and composable code. </b>
<b> In this we filter the JSON data after reading from the file in a effect full way.As we know read and write to are produces side .</b>
<b> We Use ZIO monad to convert these side effect into pure effects.</b>
<b> For parsing the JSON data we uses the zio-json library as it encode and decode JSON data in a effectfull .</b>

#### Library Dependencies 
```
libraryDependencies ++=Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio"          %% "zio-test"         % zioVersion % "test",
    "dev.zio"          %% "zio-test-sbt"     % zioVersion % "test",
    "dev.zio" %% "zio-json"    % "0.1.5"
  )
```
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
