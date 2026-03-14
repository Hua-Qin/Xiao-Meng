package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SettingsPanel(
    visible: Boolean,
    onDismiss: () -> Unit,
    memoryMode: String = "Default",
    onMemoryModeChange: (String) -> Unit,
    memoryAttached: Boolean = true,
    onMemoryAttachedChange: (Boolean) -> Unit,
    manualMemoryUpdate: Boolean = false,
    onManualMemoryUpdateChange: (Boolean) -> Unit,
    autoRead: Boolean = false,
    onAutoReadChange: (Boolean) -> Unit,
    autoApprove: Boolean = false,
    onAutoApproveChange: (Boolean) -> Unit,
    disableStream: Boolean = false,
    onDisableStreamChange: (Boolean) -> Unit,
    disableTools: Boolean = false,
    onDisableToolsChange: (Boolean) -> Unit,
    disableUserPreference: Boolean = false,
    onDisableUserPreferenceChange: (Boolean) -> Unit,
    disableLatex: Boolean = false,
    onDisableLatexChange: (Boolean) -> Unit,
    onToolManagementClick: () -> Unit
) {
    if (visible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onDismiss)
            ) {
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable(onClick = { })
                        .clip(RoundedCornerShape(DialogCornerRadius)),
                    shape = RoundedCornerShape(DialogCornerRadius),
                    colors = CardDefaults.cardColors(
                        containerColor = ChatColors.dialogBackground()
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MemorySection(
                            memoryMode = memoryMode,
                            onMemoryModeChange = onMemoryModeChange,
                            memoryAttached = memoryAttached,
                            onMemoryAttachedChange = onMemoryAttachedChange,
                            manualMemoryUpdate = manualMemoryUpdate,
                            onManualMemoryUpdateChange = onManualMemoryUpdateChange
                        )
                        
                        HorizontalDivider(
                            thickness = DividerThickness,
                            color = ChatColors.dividerColor()
                        )
                        
                        FunctionSwitchesSection(
                            autoRead = autoRead,
                            onAutoReadChange = onAutoReadChange,
                            autoApprove = autoApprove,
                            onAutoApproveChange = onAutoApproveChange
                        )
                        
                        HorizontalDivider(
                            thickness = DividerThickness,
                            color = ChatColors.dividerColor()
                        )
                        
                        DisabledItemsSection(
                            disableStream = disableStream,
                            onDisableStreamChange = onDisableStreamChange,
                            disableTools = disableTools,
                            onDisableToolsChange = onDisableToolsChange,
                            disableUserPreference = disableUserPreference,
                            onDisableUserPreferenceChange = onDisableUserPreferenceChange,
                            disableLatex = disableLatex,
                            onDisableLatexChange = onDisableLatexChange
                        )
                        
                        HorizontalDivider(
                            thickness = DividerThickness,
                            color = ChatColors.dividerColor()
                        )
                        
                        Button(
                            onClick = onToolManagementClick,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = ChatShapes.buttonShape
                        ) {
                            Text(
                                text = "工具管理",
                                style = ChatTypography.buttonText(),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MemorySection(
    memoryMode: String,
    onMemoryModeChange: (String) -> Unit,
    memoryAttached: Boolean,
    onMemoryAttachedChange: (Boolean) -> Unit,
    manualMemoryUpdate: Boolean,
    onManualMemoryUpdateChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val memoryOptions = listOf("Default", "Minimal", "Extended", "None")
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "记忆选项",
            style = MaterialTheme.typography.titleSmall,
            color = ChatColors.dialogTitle()
        )
        
        Box {
            OutlinedTextField(
                value = memoryMode,
                onValueChange = {},
                readOnly = true,
                label = { Text("记忆模式") },
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = ChatShapes.buttonShape,
                textStyle = MaterialTheme.typography.bodyMedium
            )
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                memoryOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onMemoryModeChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        
        SwitchItem(
            label = "记忆附着",
            checked = memoryAttached,
            onCheckedChange = onMemoryAttachedChange
        )
        
        SwitchItem(
            label = "手动更新记忆",
            checked = manualMemoryUpdate,
            onCheckedChange = onManualMemoryUpdateChange
        )
    }
}

@Composable
private fun FunctionSwitchesSection(
    autoRead: Boolean,
    onAutoReadChange: (Boolean) -> Unit,
    autoApprove: Boolean,
    onAutoApproveChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "功能开关",
            style = MaterialTheme.typography.titleSmall,
            color = ChatColors.dialogTitle()
        )
        
        SwitchItem(
            label = "自动朗读",
            checked = autoRead,
            onCheckedChange = onAutoReadChange
        )
        
        SwitchItem(
            label = "自动批准",
            checked = autoApprove,
            onCheckedChange = onAutoApproveChange
        )
    }
}

@Composable
private fun DisabledItemsSection(
    disableStream: Boolean,
    onDisableStreamChange: (Boolean) -> Unit,
    disableTools: Boolean,
    onDisableToolsChange: (Boolean) -> Unit,
    disableUserPreference: Boolean,
    onDisableUserPreferenceChange: (Boolean) -> Unit,
    disableLatex: Boolean,
    onDisableLatexChange: (Boolean) -> Unit
) {
    val disabledCount = listOf(
        disableStream, disableTools, disableUserPreference, disableLatex
    ).count { it }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "禁用项",
                style = MaterialTheme.typography.titleSmall,
                color = ChatColors.dialogTitle()
            )
            Text(
                text = "$disabledCount/4",
                style = MaterialTheme.typography.bodySmall,
                color = ChatColors.dialogText()
            )
        }
        
        SwitchItem(
            label = "禁用流式输出",
            checked = disableStream,
            onCheckedChange = onDisableStreamChange
        )
        
        SwitchItem(
            label = "禁用工具",
            checked = disableTools,
            onCheckedChange = onDisableToolsChange
        )
        
        SwitchItem(
            label = "禁用户偏好描述",
            checked = disableUserPreference,
            onCheckedChange = onDisableUserPreferenceChange
        )
        
        SwitchItem(
            label = "禁用LaTeX描述",
            checked = disableLatex,
            onCheckedChange = onDisableLatexChange
        )
    }
}

@Composable
private fun SwitchItem(
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
            style = MaterialTheme.typography.bodyMedium,
            color = ChatColors.dialogText(),
            modifier = Modifier.weight(1f)
        )
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .width(SwitchTrackWidth)
                .height(SwitchTrackHeight)
        )
    }
}
