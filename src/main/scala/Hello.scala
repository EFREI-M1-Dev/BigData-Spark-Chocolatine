import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.io.Source

object Hello {

  Logger.getLogger("org").setLevel(Level.ERROR)
  def main(args: Array[String]) = {
    val conf: SparkConf = new SparkConf().setAppName("name").setMaster("local")
      .set("spark.testing.memory", "2147480000")
    val sc: SparkContext = new SparkContext(conf)


    try {
      val spark = SparkSession.builder()
        .appName("Hello")
        .config("spark.master", "local")
        .getOrCreate()

      // Chargez le fichier texte
      val inputFile = "C:\\Users\\theor\\IdeaProjects\\elegy\\src\\main\\scala\\communes.csv" // Remplacez par le chemin vers votre fichier CSV
      val df = spark.read.csv(inputFile)
        .toDF("code_commune", "nom_commune", "code_departement")

      df.createOrReplaceTempView("ville_data")

      val query =
        """
           SELECT SUBSTRING(code_departement, 1, 2) AS code_departement, COUNT(*) AS nombre_de_villes
           FROM ville_data
           GROUP BY SUBSTRING(code_departement, 1, 2)
           ORDER BY CAST(code_departement AS INT) ASC
         """
      val result = spark.sql(query)
      result.show(result.count().toInt, false)


      val query2 =
        """
           SELECT nom_commune
           FROM ville_data
            WHERE LOWER(nom_commune) LIKE '%argonne%'
            ORDER BY nom_commune
         """
      val result2 = spark.sql(query2)
      result2.show(result2.count().toInt, false)


      // Arrêtez la session Spark
      spark.stop()
    } catch {
      case e: Exception =>
        println(s"Une erreur s'est produite : ${e.getMessage}")
    }
  }
}