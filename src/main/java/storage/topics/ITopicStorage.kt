package storage.topics

import domain.Question
import domain.Topic

interface ITopicStorage {
    fun getAllTopics(): List<Topic>
    fun addNewTopic(topic: Topic): Unit
    fun getTopicWithIdOf(id: String): Topic?
    fun getTopicDataWithTopicNameOf(topic: String): List<Topic>
    fun addQuestionToTopic(question: Question, topicId: String)
}