<h3 align=center>Testing In ZIO</h3>
<p>To get started with testing ensure that you have added library dependencies to your "build.sbt" file.</p>
<p><b>Dependencies </b> <br>
<code>
val zioVersion = "1.0.14"<br><br>
libraryDependencies ++= Seq(<br>
"dev.zio" %% "zio" % zioVersion,<br>
"dev.zio" %% "zio-test" % zioVersion,<br>
"dev.zio" %% "zio-test-sbt" % zioVersion<br>
)</code>
</p>

<p><b>For Better Understanding</b><br>So in BasicExamples package there is a class and under that there are a couple of arithmetic methods, and same goes with ConsoleProgram package. Once We understand what these methods do we can straight away start with executing testcases that are present in the respective test packages. </p>