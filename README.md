<h2>ZIO HTTP SIMPLE SERVER</h2>
<p>To follow along this template add the following dependencies to your "build.sbt" file.</p>
<code>val zioVersion = "1.0.14"</code><br>
<code>val ZHTTPVersion = "1.0.0.0-RC27"</code>

<code>libraryDependencies ++= Seq( <br>
"dev.zio" %% "zio" % zioVersion,<br>
"dev.zio" %% "zio-test" % zioVersion,<br>
"dev.zio" %% "zio-test-sbt" % zioVersion,<br>
"io.d11" %% "zhttp"      % ZHTTPVersion,<br>
"io.d11" %% "zhttp-test" % ZHTTPVersion % Test,<br>
)</code>

Under src/main/scala/SimpleServer package there is a scala file that holds the code snippets demonstrating how to setup a simple/minimal server to get started with zio-http library. 