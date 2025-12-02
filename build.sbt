ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

lazy val sparkVersion ="4.0.1"
lazy val scalatestVersion = "3.2.19"

lazy val root = (project in file("."))
  // https://github.com/sbt/sbt-unidoc#how-to-unify-scaladoc
  .enablePlugins(ScalaUnidocPlugin)
  .aggregate(application, sparkFramework)
  .settings(
    name := "sample-spark",
    ScalaUnidoc / unidoc / unidocProjectFilter := inAnyProject -- inProjects(application)
  )

// Spark Software Framework Project
lazy val sparkFramework = (project in file("sparkFramework"))
  .settings(
    //commonSettings,
    name := "sparkFramework",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion,
    )
  )

// TODO: Databricks Specific Software Framework Project

// Spark Testing Framework Project
lazy val sparkTestFramework = (project in file("sparkTestFramework"))
  .dependsOn(sparkFramework)
  .settings(
    name := "sparkTestFramework",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalatestVersion,
    )
  )


// Application Project
lazy val application = (project in file("application"))
  .dependsOn(sparkFramework)
  .dependsOn(sparkTestFramework % Test)
  .settings(
    name := "application", version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    )
  )

// IntegrationTest用プロジェクト
lazy val integration = (project in file("integration"))
  .dependsOn(root)
  .dependsOn(sparkTestFramework % Test)
  .settings(
    publish / skip := true,
  )

