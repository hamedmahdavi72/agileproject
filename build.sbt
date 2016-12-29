name := "agileproject"

version := "1.0"

lazy val `agileproject` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs, "uk.co.panaxiom" %% "play-jongo" % "1.0.1-jongo1.2",
  "org.mockito" % "mockito-all" % "1.9.5" )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

herokuAppName in Compile := "calm-castle-63283"