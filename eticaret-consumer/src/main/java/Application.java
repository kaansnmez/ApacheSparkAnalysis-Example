import com.mongodb.spark.MongoSpark;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir","C:\\hadoop-common-2.2.0-bin-master");
        StructType schema = new StructType()
                .add("search", DataTypes.StringType)
                .add("region",DataTypes.StringType)
                .add("current_ts",DataTypes.StringType)
                .add("userid",DataTypes.IntegerType);
        SparkSession sparkSession = SparkSession.builder().master("local")
                .appName("SparkSearchAnalysis")
                .config("spark.mongodb.output.uri", "mongodb://139.59.210.27/eticaret.popularproduct")
                .getOrCreate();
        Dataset<Row> loadDS = sparkSession.read().format("kafka")
                .option("kafka.bootstrap.servers", "139.59.210.27:9092")
                .option("subscribe", "search-analysis-userIDv2")
                .load();
        Dataset<Row> rowDataset = loadDS.selectExpr("CAST(value AS STRING)");
        Dataset<Row> valueDS = rowDataset.select(functions.from_json(rowDataset.col("value"), schema).as("jsontostructs")).select("jsontostructs.*");
        Dataset<Row> current_ts_window = valueDS.groupBy(functions.window(valueDS.col("current_ts"), "30 minute"), valueDS.col("search")).count();
        MongoSpark.write(current_ts_window).option("collection","TimeWindowSearch").save();
        /* En çok arama yapılan 10 şehir
        Dataset<Row> search = valueDS.groupBy("search").count();
        Dataset<Row> searchResult = search.sort(functions.desc("count")).limit(10);
        searchResult.show();
        MongoSpark.write(searchResult).mode("overwrite").save();
         */
        /* User İd en çok aranan
        Dataset<Row> count = valueDS.groupBy("userid", "search").count();
        Dataset<Row> filters = count.filter("count >10");
        Dataset<Row> pivot = filters.groupBy("userid").pivot("search").count().na().fill(0);
        MongoSpark.write(pivot).option("collection","searchbyUserID").mode("overwrite").save();


         */


    }
}
