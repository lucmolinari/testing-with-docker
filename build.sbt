lazy val root = (project in file("."))
  .configs(config("it") extend Test)
  .settings(
    name := "testing-with-docker",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "mysql" % "mysql-connector-java" % "5.1.42",
       "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    ),
    parallelExecution in IntegrationTest := false,
    parallelExecution in Test := false,
    Defaults.itSettings
  )
