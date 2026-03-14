package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onWindowClick: () -> Unit = {},
    onPreviousChat: () -> Unit = {},
    onNextChat: () -> Unit = {},
    canNavigateToPrevious: Boolean = true,
    canNavigateToNext: Boolean = true
) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .fillMaxWidth()
            .height(ChatTopBarHeight)
            .background(ChatColors.topBarBackground())
    ) {
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxWidth(),
            color = ChatColors.topBarBackground()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier
                        .height(IconButtonSize)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "菜单",
                        tint = ChatColors.iconButtonTint(),
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AI 对话 - 新对话 [$currentTime]",
                        style = ChatTypography.topBarTitle(),
                        color = ChatColors.topBarTitle(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onPreviousChat,
                        enabled = canNavigateToPrevious,
                        modifier = Modifier
                            .height(IconButtonSize)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "上一个对话",
                            tint = if (canNavigateToPrevious) ChatColors.iconButtonTint() else ChatColors.iconButtonTint().copy(alpha = 0.3f),
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    IconButton(
                        onClick = onNextChat,
                        enabled = canNavigateToNext,
                        modifier = Modifier
                            .height(IconButtonSize)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "下一个对话",
                            tint = if (canNavigateToNext) ChatColors.iconButtonTint() else ChatColors.iconButtonTint().copy(alpha = 0.3f),
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    IconButton(
                        onClick = onWindowClick,
                        modifier = Modifier
                            .height(IconButtonSize)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Window,
                            contentDescription = "窗口模式",
                            tint = ChatColors.iconButtonTint(),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}
