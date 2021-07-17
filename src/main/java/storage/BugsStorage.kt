package storage

import domain.Bug
import domain.User

class BugsStorage : IBugsStorage {
    override fun filterBugs(): List<Bug> {
        return listOf(Bug("1", "NPE", "NPE EXCEPTION", User("1", "nika", "123", "nika@email.com")))
    }
}