package storage.topics

import domain.Question
import domain.Topic
import io.vertx.ext.jdbc.JDBCClient

class TopicStorage(val jdbcClient: JDBCClient) : ITopicStorage {
    override fun getAllTopics(callback: (List<Topic>) -> Unit) {
        jdbcClient.query("SELECT * from topic") {
            if (it.succeeded()) {
                val result = it.result()
                val listOfTopics = result.results
                    .map { Topic(it.getInteger(0), it.getString(1), it.getString(2)) }
                    .toCollection(mutableListOf())
                callback(listOfTopics)
            } else {
                print(it.cause())
            }
        }
    }

    override fun addNewTopic(topic: Topic) {

    }

    override fun getTopicWithIdOf(id: String): Topic? {
        return null
    }

    override fun getTopicDataWithTopicNameOf(topic: String): List<Topic> {
        return mutableListOf()
    }

    override fun addQuestionToTopic(question: Question, topicId: String) {
    }
}