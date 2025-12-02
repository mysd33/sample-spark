package com.example.fw.infra.dataaccess.impl

import org.apache.spark.sql._

import ReaderMethodBuilder._
import WriterMethodBuilder._
import com.example.fw.domain.model.XmlModel

/**
 * XmlModelに対応したファイルアクセス機能
 *
 */
class XmlReaderWriter {
  /**
   * Xmlファイルを読み込みDataFrameを返却する
   *
   * @param input        入力のXmlModel
   * @param sparkSession SparkSession
   * @return DataFrame
   */
  def readToDf(input: XmlModel[Row], sparkSession: SparkSession): DataFrame = {
    val reader = input.rowTag match {
      case Some(rowTag) => sparkSession.read.option("rowTag", rowTag)
      case _ => sparkSession.read
    }
    val reader2 = input.encoding match {
      //spark-xmlではencodingではなくcharset
      case Some(encoding) => reader.option("charset", encoding)
      case _ => reader
    }
    reader2
      //暗黙の型変換でメソッド拡張
      .buildSchema(input)
      .xml(input.absolutePath)
  }

  /**
   * 引数で受け取ったDataset/DataFrameを、指定のXmlファイルに出力する
   *
   * @param ds       出力対象のDataset/DataFrame
   * @param output   出力先のXmlModel
   * @param saveMode 出力時のSaveMode
   * @tparam T CsvModelの型パラメータ
   */
  def writeFromDsDf[T](ds: Dataset[T], output: XmlModel[T], saveMode: SaveMode): Unit = {
    val writer = ds.write.mode(saveMode)
    val writer2 = output.rootTag match {
      case Some(rootTag) => writer.option("rootTag", rootTag)
      case _ => writer
    }
    val writer3 = output.rowTag match {
      case Some(rowTag) => writer2.option("rowTag", rowTag)
      case _ => writer2
    }
    writer3
      //暗黙の型変換でメソッド拡張
      .buildOptionCompression(output)
      .xml(output.absolutePath)
  }

}
