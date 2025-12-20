package com.example.app

import com.example.fw.app.DatabricksConnectApplicationEntryPoint
import com.example.fw.domain.utils.ResourceBundleManager
import org.apache.spark.SparkContext

object EntryPoint extends DatabricksConnectApplicationEntryPoint {
  def main(args: Array[String]): Unit = {
    run(args)
  }

  override def addJar(sc: SparkContext): Unit = {
    //DatabricksConnectの場合、必要なクラスの入ったjarを追加する必要があるのでsbt assemblyを実行しておくこと
    sc.addJar("target/scala-2.13/sample-spark-assembly-0.1.0.jar")
 }
}
