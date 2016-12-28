logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://dl.bintray.com/typesafe/maven-releases/"

resolvers += "heroku-sbt-plugin-releases" at "https://dl.bintray.com/heroku/sbt-plugins/"

resolvers += "heroku-sbt-plugin-releases" at "https://dl.bintray.com/heroku/sbt-plugins/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.2")

addSbtPlugin("com.heroku" % "sbt-heroku" % "1.0.1")

