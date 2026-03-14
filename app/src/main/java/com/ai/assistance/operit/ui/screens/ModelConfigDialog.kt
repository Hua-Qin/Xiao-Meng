package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ModelConfigDialog(
    visible: Boolean,
    thinkingEnabled: Boolean = false,
    thinkingGuidanceEnabled: Boolean = false,
    maxModeEnabled: Boolean = false,
    selectedModel: String? = null,
    onThinkingToggle: (Boolean) -> Unit = {},
    onThinkingGuidanceToggle: (Boolean) -> Unit = {},
    onMaxModeToggle: (Boolean) -> Unit = {},
    onDefaultConfig: () -> Unit = {},
    onManageConfig: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onModelSelect: (String) -> Unit = {}
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ChatShapes.dialogShape),
            colors = CardDefaults.cardColors(
                containerColor = ChatColors.dialogBackground()
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "模型配置",
                        style = ChatTypography.dialogTitle(),
                        color = ChatColors.dialogTitle()
                    )
                    
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(onClick = onDismiss)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "×",
                            style = MaterialTheme.typography.titleMedium,
                            color = ChatColors.dialogText()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "思考: ${if (thinkingEnabled) "on" else "off"}",
                        style = ChatTypography.dialogText(),
                        color = ChatColors.dialogText()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                SwitchOption(
                    label = "思考模式",
                    checked = thinkingEnabled,
                    onCheckedChange = onThinkingToggle
                )

                Spacer(modifier = Modifier.height(12.dp))

                SwitchOption(
                    label = "思考引导",
                    checked = thinkingGuidanceEnabled,
                    onCheckedChange = onThinkingGuidanceToggle
                )

                Spacer(modifier = Modifier.height(12.dp))

                SwitchOption(
                    label = "Max 模式",
                    checked = maxModeEnabled,
                    onCheckedChange = onMaxModeToggle
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { 
                            onModelSelect(selectedModel ?: "")
                            onDismiss()
                        }
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "模型: ${selectedModel ?: "未选择"}",
                            style = ChatTypography.dialogText(),
                            color = ChatColors.dialogText()
                        )
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDefaultConfig,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(ChatShapes.buttonShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            text = "默认配置",
                            style = ChatTypography.buttonText(),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    TextButton(
                        onClick = onManageConfig,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(ChatShapes.buttonShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "管理配置",
                            style = ChatTypography.buttonText(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SwitchOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = ChatTypography.dialogText(),
            color = ChatColors.dialogText()
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.width(SwitchTrackWidth)
        )
    }
}
