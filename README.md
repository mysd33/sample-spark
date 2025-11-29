# Hadoop SparkのサンプルAP

## 概要
- 作成中です。
    - 以前作成した[Hadoop SparkのサンプルAP](https://github.com/mysd33/spark_dev)が、古いHadoop/Sparkのバージョンのままのため、最新バージョンに対応させ移植する予定。

- 利用バージョン
    - Corretto JDK 21
    - Scala 2.13
    - Hadoop 3.4.0
    - Apache Spark 4.0.1

## プロジェクト構成
- TBD

## サンプルAPの動作確認手順
- TBD

## ソフトウェアフレームワーク
- 以前作成した[Hadoop SparkのサンプルAP](https://github.com/mysd33/spark_dev)をもとにした機能を提供予定

## WindowsでのApache Spark環境構築手順
- 参考: [https://sparkbyexamples.com/spark/apache-spark-installation-on-windows/](https://sparkbyexamples.com/spark/apache-spark-installation-on-windows/)

- Corretto JDK 21のインストール

    ```sh
    winget install -e --id Amazon.Corretto.21.JDK
    ```    

- Apache Sparkのインストール
    - Apache Sparkの[公式サイト](https://spark.apache.org/downloads.html)からダウンロードし、任意のフォルダに展開する。
        - Choose a Spark release: 4.0.1
        - Choose a package type: Pre-built for Apache Hadoop 3.4 and later
        - Download Spark: spark-4.0.1-bin-hadoop3.tgz
    - ここでは、`C:\Java\spark-4.0.1-bin-hadoop3`に展開したとする。

- 環境変数の設定
    - `JAVA_HOME`
        - Corretto JDK 21のインストール先を指定する。
        - 例: `C:\Program Files\Amazon Corretto\jdk21.0.7_6`
    - `SPARK_HOME`
        - Apache Sparkのインストール先を指定する。
        - 例: `C:\Java\spark-4.0.1-bin-hadoop3`
    - `HADOOP_HOME`
        - Apache Sparkのインストール先を指定する。
        - 例: `C:\Java\spark-4.0.1-bin-hadoop3`
    - `PATH`
        - 以下を追加する。
            - `%JAVA_HOME%\bin`
            - `%SPARK_HOME%\bin`

- winutils.exeの配置
    - `winutils.exe`を[こちら](https://github.com/kontext-tech/winutils/tree/master/hadoop-3.4.0-win10-x64/bin)からダウンロードする
    - `winutils.exe`を`%HADOOP_HOME%\bin`（C:\Java\spark-4.0.1-bin-hadoop3\bin）に配置する。

- Sparkの動作確認
    - spark-shellを起動する。
    
    ```sh
    spark-shell
    ```

## IntelliJ IDEAの開発環境構築手順
- IntelliJ IDEA Community Editionのインストール

    ```sh    
    winget install -e --id JetBrains.IntelliJIDEA.Community
    ```

- Scalaプラグインのインストール
    - 参考: [https://pleiades.io/help/idea/get-started-with-scala.html](https://pleiades.io/help/idea/get-started-with-scala.html)

    ```sh

    cd C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.1.3\bin
    idea64.exe installPlugins org.intellij.scala
    ```

- IntelliJで、Scalaのプロジェクトの作成
    - sbtをビルドツールとして利用する。
    - Scalaのバージョン: 2.13.18
