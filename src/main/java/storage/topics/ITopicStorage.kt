package storage.topics

import domain.Question
import domain.Topic

interface ITopicStorage {
    fun getAllTopics(callback: (List<Topic>) -> Unit)
    fun addNewTopic(topic: Topic)
    fun getTopicWithIdOf(id: String): Topic?
    fun getTopicDataWithTopicNameOf(topic: String): List<Topic>
    fun addQuestionToTopic(question: Question, topicId: String)
}