package storage.question

import domain.Message
import domain.Question
import io.vertx.core.json.JsonArray
import io.vertx.ext.jdbc.JDBCClient

class QuestionStorage(private val jdbcClient: JDBCClient) : IQuestionStorage {
    val questionInsertPreparedQuery =
        "insert into question(title, content, email, isactive, topic_id) values(?, ?, ?, ? ,?)"

    override fun addNewQuestion(question: Question, topicId: Int) {
        jdbcClient.updateWithParams(
            questionInsertPreparedQuery,
            JsonArray().add(question.title)
                .add(question.content)
                .add(question.email)
                .add(question.isActive)
                .add(topicId)
        ) {
        }
    }

    override fun getMessagesToTopic(topicId: Int, callback: (List<Message>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllQuestions(callback: (List<Message>) -> Unit) {
        TODO("Not yet implemented")
    }

}