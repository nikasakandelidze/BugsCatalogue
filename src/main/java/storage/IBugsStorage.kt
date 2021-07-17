package storage

import domain.Bug

interface IBugsStorage {
    fun filterBugs(): List<Bug>
}