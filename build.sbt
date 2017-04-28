name := """eth-propeller-scala"""

organization := "org.adridadou"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "java-ipfs-api-mvn-repo" at "https://raw.github.com/pascr/java-ipfs-api/mvn-repo/",
  "adridadou-bintray" at "https://dl.bintray.com/cubefriendly/maven/",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.adridadou" % "eth-propeller-core" % "0.1",
  "io.reactivex" %% "rxscala" % "0.26.5",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  //Test libs
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
)

fork in run := true
