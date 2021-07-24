package storage.question

import domain.Message
import domain.Question

interface IQuestionStorage {
    fun addNewQuestion(question: Question, topicId: Int, callback: (Unit) -> Unit)
    fun getMessagesToTopic(topicId: Int, callback: (List<Message>) -> Unit)
    fun getAllQuestions(callback: (List<Message>) -> Unit)
}