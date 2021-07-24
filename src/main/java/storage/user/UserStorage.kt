package storage.user

import domain.User
import io.vertx.core.json.JsonArray
import io.vertx.ext.jdbc.JDBCClient

class UserStorage(private val jdbcClient: JDBCClient) : IUserStorage {
    private val selectUsersForTopicQuery =
        "SELECT * from users left outer join topic_user_subscription on users.id = topic_user_subscription.user_id where topic_user_subscription.topic_id = ?"

    override fun getAllUsersSubscribedToTopicWithIdOf(topicId: Int, callback: (List<User>) -> Unit) {
        jdbcClient.queryWithParams(selectUsersForTopicQuery, JsonArray().add(topicId)) {
            if (it.succeeded()) {
                val result = it.result()
                val listOfTopics = result.results
                    .map { User(it.getInteger(0), it.getString(1), it.getString(2), it.getString(3), it.getString(4), it.getString(5)) }
                    .toCollection(mutableListOf())
                callback(listOfTopics)
            } else {
                print(it.cause())
            }
        }
    }

}