package cz.upce.vetalmael.messages

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_message_client.*
import kotlinx.android.synthetic.main.item_message_client.text_message_body
import kotlinx.android.synthetic.main.item_message_client.text_message_time
import kotlinx.android.synthetic.main.item_message_doctor.*

fun myMessageDelegate() = adapterDelegateLayoutContainer<
        MyMessageViewData, MessagesViewData>(R.layout.item_message_client) {
    bind {
        text_message_body.text = item.message
        text_message_time.text = item.time
    }
}

fun doctorMessageDelegate() = adapterDelegateLayoutContainer<
        DoctorMessageViewData, MessagesViewData>(R.layout.item_message_doctor) {
    bind {
        text_message_name.text = item.author
        text_message_body.text = item.message
        text_message_time.text = item.time
    }
}