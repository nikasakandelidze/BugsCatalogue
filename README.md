# Problem

In the most of the places we are growing in: working places, universities, courses.. etc have some overhead with "asking
big questions" associated with it. Before we continue further let me explain exact definitions of next 2 words:

1) big question - a kind of a question, which can't be answered in less that 10 words. The answer to which might be so
   complicated, that this answer is better to be responded in the written form.
2) overhead - sometimes there are some factors that block you from moving from asking a question to next task/milestone.
   The factors for this might be several: the question is too long so you spend long time awaiting, person is not
   available right now to answer, you are waiting for the answer, cause you are a good human being, even though you
   don't need answer right now.. etc. sometimes answer responded is so big that you regret why you didn't ask for the
   answer in written form and etc, sometimes you just dont want to talk to a person and want to have some
   threshold/barrier between you and the answerer using email or smth.

# Solution

Platform/System that lets any user post a question to some specific topic and not to a specific user. System balances
and dispatches question to and some(some<=count(all)) users who are subscribed as "Answerers" to this specific topic.
"Answeres"(users who are subscribed to this topic to be eligable to answer) - get 2 kinds of notifications:

1) in the platform commentaries
2) by the email

# Usage
You can use this system inside your company, university or any place to decouple people from each others blocking questions, make them more productive and
have a storage/history of all asked questions that where about: some specific topics from inside of company, culture, some technology or code and etc.

# Technologies/stacks used
Java - Vert.x framework, Postgresql, Docker, bash script
![post question flow](media/post-question-flow.png)
