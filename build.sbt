ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.18"

lazy val sparkVersion ="4.0.1"
lazy val scalatestVersion = "3.2.19"
lazy val mockitoVersion = "2.0.0"

lazy val commonSettings = Seq(
  //sbt assemblyで、テストをスキップ
  assembly / test := {},
  autoAPIMappings := true,
  scalacOptions ++= Seq("-encoding", "UTF-8")
)

lazy val root = (project in file("."))
  // https://github.com/sbt/sbt-unidoc#how-to-unify-scaladoc
  .enablePlugins(ScalaUnidocPlugin)
  .aggregate(integration, application, sparkFramework)
  .settings(
    commonSettings,
    name := "sample-spark",
    ScalaUnidoc / unidoc / unidocProjectFilter := inAnyProject -- inProjects(application)
  )

// Spark Software Framework Project
lazy val sparkFramework = (project in file("sparkFramework"))
  .settings(
    commonSettings,
    name := "sparkFramework",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion
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
    commonSettings,
    name := "application", version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalatestVersion % Test,
      "org.mockito" %% "mockito-scala" % mockitoVersion % Test,
    ),
    Test / fork := true,
    // RDDを使った動作確認には、JDK 9以降のモジュールシステムの影響で以下のJVMオプションが必要
    Test / javaOptions ++= Seq(
      "--add-opens=java.base/java.nio=ALL-UNNAMED",
      "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
      "--add-opens=java.base/java.util=ALL-UNNAMED",
      "-Dactive.profile=ut"
    )
  )

// IntegrationTest用プロジェクト
lazy val integration = (project in file("integration"))
  .dependsOn(application)
  .dependsOn(sparkTestFramework % Test)
  .settings(
    commonSettings,
    Test / fork := true,
    // RDDを使った動作確認には、JDK 9以降のモジュールシステムの影響で以下のJVMオプションが必要
    Test / javaOptions ++= Seq(
      "--add-opens=java.base/java.nio=ALL-UNNAMED",
      "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
      "--add-opens=java.base/java.util=ALL-UNNAMED",
      "-Dactive.profile=it"
    )
  )

