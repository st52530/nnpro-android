package cz.upce.vetalmael.core.viewmodel.data

class Event<T>(private var data: T) {

    private var isLocked = false

    @Synchronized
    fun consume(): T? {
        if (!isLocked) {
            isLocked = true

            return data
        }

        return null
    }
}