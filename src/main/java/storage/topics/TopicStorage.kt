package storage.topics

import domain.Question
import domain.Topic

class TopicStorage : ITopicStorage {
    private val topics: MutableList<Topic> = mutableListOf(
        Topic("1", "java", "Topic related to Java and JVM stack/frameworks", mutableListOf()),
        Topic("2", "javascript", "Topic related to JS", mutableListOf()),
        Topic("3", "python", "Topic related to python programming", mutableListOf())
    )

    override fun getAllTopics(): MutableList<Topic> {
        return topics
    }

    override fun addNewTopic(topic: Topic) {
        topics.add(topic)
    }

    override fun getTopicWithIdOf(id: String): Topic? {
        return topics.find { it.id == id }
    }

    override fun getTopicDataWithTopicNameOf(topic: String): List<Topic> {
        return topics.filter { it.title == topic }
    }

    override fun addQuestionToTopic(question: Question, topicId: String) {
        topics
            .filter { it.id == topicId }
            .forEach {
                it.questions?.add(question)
            }
    }
}