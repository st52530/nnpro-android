package cz.upce.vetalmael.messages

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.upce.vetalmael.R
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.model.Message
import cz.upce.vetalmael.data.model.UserRole
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.contentLoadinglayout
import kotlinx.android.synthetic.main.fragment_messages.emptyStateLayout
import kotlinx.android.synthetic.main.fragment_messages.toolbar
import kotlinx.android.synthetic.main.include_empty_state.*
import timber.log.Timber
import java.text.SimpleDateFormat

class MessagesFragment(
    private val animalRepository: AnimalRepository
) : Fragment(R.layout.fragment_messages) {

    private val adapter: DiffUtilAdapter<MessagesViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            myMessageDelegate(),
            doctorMessageDelegate()
        )
    }

    private val navigationArgs by navArgs<MessagesFragmentArgs>()

    private val timeFormat = SimpleDateFormat("HH:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        emptyStateTextView.setText(R.string.empty_message_list)

        contentLoadinglayout.setTryAgainListener {
            loadMessages()
        }

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        messageEditText.doOnTextChanged { text, _, _, _ ->
            sendButton.isEnabled = text?.isNotEmpty() == true
        }

        sendButton.setOnClickListener {
            sendMessage()
        }

        loadMessages()
    }

    private fun sendMessage() {
        lifecycleScope.launchWhenCreated {
            try {
                val message = animalRepository.sendMessage(
                    navigationArgs.animalId,
                    messageEditText.text.toString()
                )
                adapter.items = adapter.items.toMutableList().apply {
                    add(message.toViewData())
                    emptyStateLayout.setVisibleOrGone(isEmpty())
                }

                messageEditText.setText("")
                sendButton.isEnabled = false
                recyclerView.post {
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    private fun loadMessages() {
        lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val messages = animalRepository.getMessages(navigationArgs.animalId)
                    .map { message ->
                        message.toViewData()
                    }
                adapter.items = messages
                emptyStateLayout.setVisibleOrGone(messages.isEmpty())
                contentLoadinglayout.showData()
            } catch (exception: Exception) {
                Timber.e(exception)
                contentLoadinglayout.showError()
            }
        }
    }

    private fun Message.toViewData(): MessagesViewData {
        return if (sender.roles == UserRole.CLIENT) {
            MyMessageViewData(
                id = idMessage.toString(),
                message = text,
                time = timeFormat.format(date)
            )
        } else {
            DoctorMessageViewData(
                id = idMessage.toString(),
                message = text,
                author = sender.fullName,
                time = timeFormat.format(date)
            )
        }
    }
}