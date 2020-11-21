package cz.upce.vetalmael.messages

import cz.upce.vetalmael.core.view.recyclerview.Identifiable

sealed class MessagesViewData : Identifiable {

    abstract val message: String

    abstract val time: String
}

data class MyMessageViewData(
    override val id: String,
    override val message: String,
    override val time: String
) : MessagesViewData()

data class DoctorMessageViewData(
    override val id: String,
    override val message: String,
    override val time: String,
    val author: String
) : MessagesViewData()