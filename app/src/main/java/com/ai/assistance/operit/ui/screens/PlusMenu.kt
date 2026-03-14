package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PlusMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onPhotoClick: () -> Unit,
    onCameraClick: () -> Unit,
    onMemoryClick: () -> Unit,
    onAudioClick: () -> Unit,
    onFileClick: () -> Unit,
    onScreenClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (expanded) {
            Popup(
                alignment = Alignment.BottomStart,
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = onDismiss
            ) {
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        MenuItem(
                            icon = Icons.Default.PhotoLibrary,
                            text = "照片",
                            onClick = {
                                onPhotoClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.CameraAlt,
                            text = "拍照",
                            onClick = {
                                onCameraClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.Psychology,
                            text = "记忆",
                            onClick = {
                                onMemoryClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.Mic,
                            text = "音频",
                            onClick = {
                                onAudioClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.AttachFile,
                            text = "文件",
                            onClick = {
                                onFileClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.Screenshot,
                            text = "屏幕内容",
                            onClick = {
                                onScreenClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.Notifications,
                            text = "当前通知",
                            onClick = {
                                onNotificationClick()
                                onDismiss()
                            }
                        )
                        MenuItem(
                            icon = Icons.Default.LocationOn,
                            text = "当前位置",
                            onClick = {
                                onLocationClick()
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
