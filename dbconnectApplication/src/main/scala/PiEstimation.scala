import org.apache.spark.sql.SparkSession

// TODO: 昔のAzure Databricksの実装からそのままのため動作未確認
object PiEstimation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("WARN")

    val NUM_SAMPLES = 1000
    val count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
      val x = math.random
      val y = math.random
      x * x + y * y < 1
    }.count()
    println(s"Pi is roughly ${4.0 * count / NUM_SAMPLES}")
    spark.stop()
  }
}
