# Hadoop SparkのサンプルAP

## 1. 概要
- 作成中です。
    - 現在、以前作成した[Hadoop SparkのサンプルAP](https://github.com/mysd33/spark_dev)が、古いHadoop/Sparkのバージョンのままだったため、最新のHadoop/Sparkバージョンに対応させて移植する作業をしています。

- 利用バージョン
    - Corretto JDK 21
    - Scala 2.13
    - Hadoop 3.4.0
    - Apache Spark 4.0.1

## 2. プロジェクト構成
- 以下のプロジェクト構成です。通常は、IntelliJ IDEAでのプロジェクトとして読み込んでください。
    - sample-spark： ルートプロジェクト
        - application： 業務APサブプロジェクト
        - integraion: 業務APの結合テストサブプロジェクト
        - sparkFramework： SparkAPのAP基盤サブプロジェクト。Spark標準機能のみに依存
        - sparkTestFramework： SparkAP用の拡張テストフレームワークサブプロジェクト
        - testdata： 業務APを動作させるためのテストデータファイルのフォルダ

## 3. WindowsでのApache Spark環境構築手順
- 通常SparkはLinux環境で動作させますが、Windows 11の端末でHadoop/Sparkの開発環境を構築する手順を示します。
    - 参考: [https://sparkbyexamples.com/spark/apache-spark-installation-on-windows/](https://sparkbyexamples.com/spark/apache-spark-installation-on-windows/)

- Corretto JDK 21のインストール
    - PowerShellを管理者権限で起動し、以下のコマンドを実行する。

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
    - `winutils.exe`と`hadoop.dll`を[こちら](https://github.com/kontext-tech/winutils/tree/master/hadoop-3.4.0-win10-x64/bin)からダウンロードする
    - `winutils.exe`と`hadoop.dll`を`%HADOOP_HOME%\bin`（C:\Java\spark-4.0.1-bin-hadoop3\bin）に配置する。

- Sparkの動作確認
    - spark-shellを起動する。

    ```sh
    spark-shell
    ```

## 4. IntelliJ IDEAの開発環境構築手順
- IntelliJ IDEA Community Editionのインストール
    - PowerShellを管理者権限で起動し、以下のコマンドを実行する。

    ```sh    
    winget install -e --id JetBrains.IntelliJIDEA.Community
    ```

- Scalaプラグインのインストール
    - 参考: [https://pleiades.io/help/idea/get-started-with-scala.html](https://pleiades.io/help/idea/get-started-with-scala.html)

    ```sh
    cd C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.1.3\bin
    idea64.exe installPlugins org.intellij.scala
    ```

- sbtのインストール
    - IntelliJ IDEAのScalaプラグインをインストールすれば、sbtは不要ですが、コマンドラインでsbtを使いたい場合は、以下の手順でインストールします。
    - PowerShellを管理者権限で起動し、以下のコマンドを実行する。

    ```sh
    winget install -e --id sbt.sbt
    ```

- IntelliJで、Scalaのプロジェクトの作成
    - sbtをビルドツールとして利用する。
    - Scalaのバージョン: 2.13.18　を指定する。

## 5. 簡単なSparkのサンプルAPのローカル動作確認手順
- ごく簡単なSparkのサンプルAPを、IntelliJから端末ローカルでSpark実行する方法です。 
- サンプルを動作させる際は、testdata/inputディレクトリにあるデータを「C:\temp」にコピーしてください
    - ファイルパスを変更したい場合は、各Scalaファイル内の該当箇所を修正してください。

- 以下に、簡単なサンプルコードを作成しているので、IntelliJで、各Scalaファイルを開いてmainメソッドを実行してください。
    - [HelloWorld.scala](./application/src/main/scala/HelloWorld.scala)
        - もっとも簡単なHello WorldのSparkアプリケーション
    - [Tutorial.scala](./application/src/main/scala/Tutorial.scala)
        - Readme.md内の単語の数を数えるSparkアプリケーション
    - [TutorialForDataset.scala](./application/src/main/scala/TutorialForDataset.scala)
        - Datasetを使ったSparkアプリケーション
    - [TutorialForParquet.scala](./application/src/main/scala/TutorialForParquet.scala)
        - Parquetファイルを使ったSparkアプリケーション
    - [SparkSQLExample.scala](./application/src/main/scala/SparkSQLExample.scala)
        - Spark SQLを使ったSparkアプリケーション


## 6. ソフトウェアフレームワーク
- TBD
    - 以前作成した[Hadoop SparkのサンプルAP](https://github.com/mysd33/spark_dev)をもとにした機能を移植中です。

| 機能            | 機能概要                                                                                                                                                                                                                                                        | 拡張実装の格納パッケージ                                                                                                |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| バッチAP実行制御     | クラウドサービスやSparkのディストリビューションによるSpark実装に応じたAPの起動方法や、ファイルやテーブルを実現するストレージの位置情報やファイル形式の差異を隠蔽し、ジョブ実行する汎用的なAPIを提供する。<br/>これにより、業務固有のロジックだけに注力して開発し、Sparkのクラスタ上でなくても、テスト実行しやすいAP構造とする。                                                                               | com.example.fw.app                                                                                          |
| ロギング          | Spark上で実行されるAPのログを出力するための汎用的なAPIを提供する。<br/>動作環境によって、ログの出力先等、ログレベル、フォーマット変更を切り替え可能にする。                                                                                                                                                                       | com.example.fw.domain.logging                                                                               |
| メッセージ管理       | ログに出力メッセージを管理する。メッセージフォーマットを変更しやすいようにプロパティファイルに切り出し、キー対する値としてメッセージ文字列を取得したり、置き換え文字（プレースホルダ）にも対応する。                                                                                                                                                          | com.example.fw.domain.message                                                                               |
| プロパティ管理       | 動作環境によって変わるパラメータの設定を管理しやすくする。<br/>プロパティファイル、OS環境変数、起動時の引数（システムプロパティ）で指定することで、設定値を切り替え可能とする。                                                                                                                                                                 | com.example.fw.domain.utils                                                                                 |
| ファイル・テーブルアクセス | クラウドサービスやSparkのディストリビューションといった実行環境の際に応じて、ファイルやテーブルの物理的配置先や通信プロトコル、データ形式（例えば、ローカルではOSファイルシステムだがクラウドだとHDFSやS3、通常のSpark上ならParquetだがDatabricks上ならParquet拡張のDeltaLakeに切り替える等）ため、テキスト、CSV、JSON、XML、Parquetといったデータ形式を抽象化した汎用的なAPIを提供し、環境によってファイル形式やファイルパスを切り替え可能にして、Sparkのクラスタ上でなくても、テスト実行しやすいAP構造とする。 | com.example.fw.domain.dataaccess<br/>com.example.fw.domain.model<br/>com.example.fw.domain.infra.dataaccess |
| 単体テストコード作成支援  | Sparkのクラウド環境がなくても、開発端末やCIサーバ上で、ビジネスロジックを単体テスト実行できるようにするためのテストコード作成支援機能を提供する。                                                                                                                                                                                | com.example.fw.test                                                                                         |
| 結合テストコード作成支援  | Sparkのクラウド環境がなくても、開発端末やCIサーバ上で、ジョブに対する処理テスト（結合テスト）を実行できるようにするためのテストコード作成支援機能を提供する。                                                                                                                                                                          | com.example.fw.it                                                                                           |


## 7. 拡張ソフトウェアフレームワークを使ったサンプルAPのローカル動作確認手順
- 本ソフトウェアフレームワークを使ったより実践的なサンプルAPをIntelliJから端末ローカルでSpark実行する方法です。

- サンプルを動作させる際は、testdata/inputディレクトリにあるデータを「C:\temp」にコピーしてください
    - applicaitonプロジェクト/src/main/resources/application-dev.propertiesのbasepathプロパティを変更すれば違うディレクトリにも変更可能です
- 拡張フレームワークを使っているため、同じメインクラスを使って指定したビジネスロジック（Logic）クラスを実行できるようになっています。

- AP起動方法
    - IntelliJの「Edit Configurations（構成の編集）」で「アプリケーション」を作成
    - 「メインクラス」に「`com.example.fw.app.ApplicationEntryPoint`」を設定
    - 「プログラムの引数」に対象Logicクラスの完全修飾名を設定
        - 例）`com.example.sample.logic.SampleDataSetBLogic3`
            - [Logicクラスのフォルダ](./application/src/main/scala/com/example/sample/logic)に、サンプルがいろいろあるので実行したいLogicクラスを選択する
    - 「VMパラメータ」に「`-Dactive.profile=dev`」を設定
        - または「環境変数」に「ACTIVE_PROFILE=dev」と設定
        - ローカル実行時の設定で動作させることができます。
    - 「作業ディレクトリ」はプロジェクトのルートフォルダを設定（通常は、デフォルトのまま）
        - 例）C:\Users\xxxx\git\sample-spark
    - 「-cp」（クラスパス）は「`sample-spark.application`」（アプリケーションのプロジェクト）を設定

- 注意事項
    - RDDを使ったロジックの実行の場合は、JDK 9以降のモジュールシステムの影響で、以下のVMオプションを追加する必要があります。
    - 「VMパラメータ」に以下を追加してください。
    
        ```
        --add-opens=java.base/java.nio=ALL-UNNAMED
        --add-opens=java.base/java.lang.invoke=ALL-UNNAMED
        --add-opens=java.base/java.util=ALL-UNNAMED
        ```

## 8. ビルド
- IntelliJのsbt shellの場合
    ```
    > package
    ```

- sbtコマンドの場合
    ```
    sbt package
    ```

## 9. 単体テスト
- 単体テストコードは、「application」プロジェクトの「src/test/scala」ディレクトリに格納します
- IntelliJで指定したテストクラス実行の場合
    - 「構成の編集」で「ScalaTest」を作成
    - 「テストクラス」に対象テストクラスを設定
        - 例）`com.example.sample.logic.UTDemoDatasetBLogicTest`
        - [テストクラスのフォルダ](./application/src/test/resources/application-ut.propertiesに、サンプルがいろいろあるので実行したいLogicクラスを選択する
    - 「VMパラメータ」に「-Dactive.profile=ut」を設定
        - または「環境変数」に「ACTIVE_PROFILE=ut」と設定
    - 「クラスパスとJDK」は「`sample-spark.application.test`」

- IntelliJのsbt shellでのテスト実行の場合  
    - 「ファイル」-「設定」で、「ビルド、実行、デプロイ」の「sbt」の設定で「VMパラメータ」で「-Dactive.profile=ut」を設定しておく
        
    - 以下実行

        ```
        > test
        ```

- sbtコマンドでのテスト実行の場合

  ```
  sbt -Dactive.profile=ut test
  ```

## 結合テスト
- TBD

## 10. 実行可能JAR（アセンブリ）の作成
- TBD

## 11. CI/CD
- TBD

## 12. EMR上でのApache Spark実行手順
- TBD
 
## 13. Step FunctionsでのApache Spark実行手順
- TBD

## 14. Step FunctionsでのApache Spark実行手順
- TBD

    


