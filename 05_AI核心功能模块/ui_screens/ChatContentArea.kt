package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ai.assistance.operit.data.model.ChatMessage
import kotlinx.coroutines.launch

@Composable
fun ChatContentArea(
    messages: List<ChatMessage> = emptyList(),
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ChatColors.aiMessageBackground())
    ) {
        if (messages.isEmpty()) {
            WelcomeMessage()
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MessageSpacing),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                }
            }
        }
    }
}

@Composable
private fun WelcomeMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "周末愉快，工作的事交给我",
            style = ChatTypography.messageText(),
            color = ChatColors.aiMessageText()
        )
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    val isUser = message.sender == "user"
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = MessageBubbleMaxWidth)
                .clip(if (isUser) ChatShapes.userMessageShape else ChatShapes.aiMessageShape)
                .background(if (isUser) ChatColors.userMessageBackground() else ChatColors.aiMessageBackground())
                .padding(MessagePadding)
        ) {
            Text(
                text = message.content,
                style = ChatTypography.messageText(),
                color = if (isUser) ChatColors.userMessageText() else ChatColors.aiMessageText()
            )
        }
    }
}
