package cz.upce.vetalmael.messages

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_message_client.messageTextView
import kotlinx.android.synthetic.main.item_message_client.timeTextView
import kotlinx.android.synthetic.main.item_message_doctor.*

fun myMessageDelegate() = adapterDelegateLayoutContainer<
        MyMessageViewData, MessagesViewData>(R.layout.item_message_client) {
    bind {
        messageTextView.text = item.message
        timeTextView.text = item.time
    }
}

fun doctorMessageDelegate() = adapterDelegateLayoutContainer<
        DoctorMessageViewData, MessagesViewData>(R.layout.item_message_doctor) {
    bind {
        authorTextView.text = item.author
        messageTextView.text = item.message
        timeTextView.text = item.time
    }
}