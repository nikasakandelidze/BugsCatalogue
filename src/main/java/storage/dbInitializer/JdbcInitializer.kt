package storage.dbInitializer

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient

class JdbcInitializer {
    fun init(vertx: Vertx, port: Int, dbName: String): JDBCClient {
        val createShared = JDBCClient.createShared(
            vertx, JsonObject()
                .put("driverClassName", "org.postgresql.Driver")
                .put("url", "jdbc:postgresql://localhost:5433/elarge")
                .put("user", "elarge")
                .put("password", "elarge")
                .put("maximumPoolSize", 30)
        )
        return createShared
    }
}