import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Communes {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("name").setMaster("local")
      .set("spark.testing.memory", "2147480000")
    val sc: SparkContext = new SparkContext(conf)

    // Créez une session Spark
    val spark = SparkSession.builder()
      .appName("Communes")
      .config("spark.master", "local")
      .getOrCreate()

    try {
      // Chargez le fichier CSV en tant que DataFrame
      val inputFile = "C:\\Users\\theor\\IdeaProjects\\elegy\\src\\main\\scala\\communes.csv" // Remplacez par le chemin vers votre fichier CSV
      val df = spark.read.csv(inputFile)

      // Sélectionnez la colonne contenant le texte à analyser (remplacez "nom_colonne" par le nom de votre colonne)
      val textColumn = "nom_commune" // Remplacez par le nom de votre colonne de texte
      val textDf = df.select(textColumn)

      // Comptez le nombre d'occurrences de la lettre "E" dans le texte
      val letterECount = textDf.rdd.map(row => row.getString(0).count(_.toUpper == 'E')).sum()

      // Affichez le résultat
      println(s"Le nombre d'occurrences de la lettre 'E' dans le document est : $letterECount")
    } finally {
      // Arrêtez la session Spark
      spark.stop()
    }
  }
}
