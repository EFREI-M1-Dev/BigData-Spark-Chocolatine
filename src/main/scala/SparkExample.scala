import org.apache.spark.sql.SparkSession

object SparkExample {
  def main(args: Array[String]): Unit = {
    // Créez une session Spark
    val spark = SparkSession.builder()
      .appName("SimpleSparkExample")
      .config("spark.master", "local")
      .getOrCreate()

    // Chargez le fichier texte
    val textFile = spark.read.textFile("elegy.txt")

    // Comptez le nombre de lignes
    val lineCount = textFile.count()

    // Affichez le résultat
    println(s"Le fichier contient $lineCount lignes.")

    // Arrêtez la session Spark
    spark.stop()
  }
}
