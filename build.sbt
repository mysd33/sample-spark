ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.18"

lazy val sparkVersion ="4.0.1"
lazy val scalatestVersion = "3.2.19"
lazy val mockitoVersion = "2.0.0"
lazy val dbUtilsVersion = "0.1.5"
lazy val dbconnectVersion = "18.0.0"

lazy val commonSettings = Seq(
  //sbt assemblyで、テストをスキップ
  assembly / test := {},
  autoAPIMappings := true,
  scalacOptions ++= Seq("-encoding", "UTF-8")
)

lazy val root = (project in file("."))
  .aggregate(integration, application, sparkFramework, databricksFramework, dbconnectApplication)
  // https://github.com/sbt/sbt-unidoc#how-to-unify-scaladoc
  .enablePlugins(ScalaUnidocPlugin)
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

// Databricks Specific Software Framework Project
lazy val databricksFramework = (project in file("databricksFramework"))
  .dependsOn(sparkFramework)
  .settings(
    commonSettings,
    name := "databricksFramework",
    libraryDependencies ++= Seq("com.databricks" %% "databricks-dbutils-scala" % dbUtilsVersion)
  )

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

// TODO: Databricks Connect Application　動作未確認
// https://docs.databricks.com/aws/ja/dev-tools/databricks-connect/scala/
lazy val dbconnectApplication = (project in file("dbconnectApplication"))
  .dependsOn(application, databricksFramework)
  .settings(
    commonSettings,
    name := "dbconnectApplication",
    libraryDependencies ++= Seq(
      "com.databricks" %% "databricks-connect" % dbconnectVersion
    ),
    fork := true,
    javaOptions ++= Seq(
      "--add-opens=java.base/java.nio=ALL-UNNAMED"
    )
  )


// IntegrationTest Project
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

