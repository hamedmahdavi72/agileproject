name := "agileproject"

version := "1.0"

lazy val `agileproject` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs, "uk.co.panaxiom" %% "play-jongo" % "1.0.1-jongo1.2",
  "org.mockito" % "mockito-all" % "1.10.8", "org.powermock" % "powermock-api-mockito" % "1.6.2",
  "org.powermock" % "powermock-module-junit4" % "1.6.2" )


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )