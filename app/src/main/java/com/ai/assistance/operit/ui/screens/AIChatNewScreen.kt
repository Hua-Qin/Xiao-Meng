package com.ai.assistance.operit.ui.features.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ai.assistance.operit.data.model.ChatMessage
import com.ai.assistance.operit.ui.features.chat.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AIChatNewScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state = remember { AIChatState() }
    val sharedPrefs = remember { context.getSharedPreferences("ai_chat_settings", Context.MODE_PRIVATE) }

    val messages = remember { mutableStateListOf<ChatMessage>() }
    var inputText by remember { mutableStateOf("") }
    var currentChatIndex by remember { mutableStateOf(0) }
    var totalChats by remember { mutableStateOf(1) }

    var thinkingEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("thinking_enabled", false)) }
    var thinkingGuidanceEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("thinking_guidance_enabled", false)) }
    var maxModeEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("max_mode_enabled", false)) }
    var selectedModel by remember { mutableStateOf(sharedPrefs.getString("selected_model", "GPT-4")) }

    var memoryMode by remember { mutableStateOf(sharedPrefs.getString("memory_mode", "Default") ?: "Default") }
    var memoryAttached by remember { mutableStateOf(sharedPrefs.getBoolean("memory_attached", true)) }
    var manualMemoryUpdate by remember { mutableStateOf(sharedPrefs.getBoolean("manual_memory_update", false)) }
    var autoRead by remember { mutableStateOf(sharedPrefs.getBoolean("auto_read", false)) }
    var autoApprove by remember { mutableStateOf(sharedPrefs.getBoolean("auto_approve", false)) }
    var disableStream by remember { mutableStateOf(sharedPrefs.getBoolean("disable_stream", false)) }
    var disableTools by remember { mutableStateOf(sharedPrefs.getBoolean("disable_tools", false)) }
    var disableUserPreference by remember { mutableStateOf(sharedPrefs.getBoolean("disable_user_preference", false)) }
    var disableLatex by remember { mutableStateOf(sharedPrefs.getBoolean("disable_latex", false)) }

    var tokensUsed by remember { mutableStateOf("14.50k") }
    var isSidebarExpanded by remember { mutableStateOf(true) }

    val showModelConfigDialog by state.showModelConfigDialog.collectAsState()
    val showPlusMenu by state.showPlusMenu.collectAsState()
    val showSettingsPanel by state.showSettingsPanel.collectAsState()
    val showIntegratedSidebar by state.showIntegratedSidebar.collectAsState()
    val isFullScreenMode by state.isFullScreenMode.collectAsState()
    val isVoiceInputMode by state.isVoiceInputMode.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            state.updateCurrentTime()
        }
    }

    LaunchedEffect(messages.size) {
        val totalTokens = messages.sumOf { it.content.length * 0.25 }
        tokensUsed = String.format("%.2fk", totalTokens / 1000)
    }

    AIChatTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopNavBar(
                    onMenuClick = { state.toggleIntegratedSidebar() },
                    onWindowClick = { state.toggleFullScreenMode() },
                    onPreviousChat = {
                        if (currentChatIndex > 0) {
                            currentChatIndex--
                            loadChatMessages(currentChatIndex)
                        }
                    },
                    onNextChat = {
                        if (currentChatIndex < totalChats - 1) {
                            currentChatIndex++
                            loadChatMessages(currentChatIndex)
                        }
                    },
                    canNavigateToPrevious = currentChatIndex > 0,
                    canNavigateToNext = currentChatIndex < totalChats - 1
                )

                SubNavBar(
                    onRefreshClick = { messages.clear() },
                    onSidebarToggle = {
                        isSidebarExpanded = !isSidebarExpanded
                    },
                    onUserAvatarClick = { state.toggleIntegratedSidebar() },
                    isSidebarExpanded = isSidebarExpanded,
                    tokensUsed = tokensUsed
                )

                ChatContentArea(
                    messages = messages,
                    modifier = Modifier.weight(1f)
                )

                BottomInputArea(
                    inputText = inputText,
                    onInputChange = { inputText = it },
                    onSendMessage = {
                        if (inputText.isNotBlank()) {
                            messages.add(ChatMessage(sender = "user", content = inputText))
                            val userInput = inputText
                            inputText = ""
                        }
                    },
                    onFullscreenClick = { state.toggleFullScreenMode() },
                    onModelConfigClick = { state.toggleModelConfigDialog() },
                    onSettingsClick = { state.toggleSettingsPanel() },
                    onAddButtonClick = { state.togglePlusMenu() },
                    onMicButtonClick = { state.toggleVoiceInputMode() }
                )
            }

            ModelConfigDialog(
                visible = showModelConfigDialog,
                thinkingEnabled = thinkingEnabled,
                thinkingGuidanceEnabled = thinkingGuidanceEnabled,
                maxModeEnabled = maxModeEnabled,
                selectedModel = selectedModel,
                onThinkingToggle = { enabled ->
                    thinkingEnabled = enabled
                    sharedPrefs.edit().putBoolean("thinking_enabled", enabled).apply()
                },
                onThinkingGuidanceToggle = { enabled ->
                    thinkingGuidanceEnabled = enabled
                    sharedPrefs.edit().putBoolean("thinking_guidance_enabled", enabled).apply()
                },
                onMaxModeToggle = { enabled ->
                    maxModeEnabled = enabled
                    sharedPrefs.edit().putBoolean("max_mode_enabled", enabled).apply()
                },
                onDefaultConfig = {
                    thinkingEnabled = false
                    thinkingGuidanceEnabled = false
                    maxModeEnabled = false
                    selectedModel = "GPT-4"
                    sharedPrefs.edit()
                        .putBoolean("thinking_enabled", false)
                        .putBoolean("thinking_guidance_enabled", false)
                        .putBoolean("max_mode_enabled", false)
                        .putString("selected_model", "GPT-4")
                        .apply()
                },
                onManageConfig = { /* TODO: Implement config management */ },
                onDismiss = { state.setModelConfigDialog(false) },
                onModelSelect = { model -> selectedModel = model }
            )

            PlusMenu(
                expanded = showPlusMenu,
                onDismiss = { state.setPlusMenu(false) },
                onPhotoClick = { /* TODO: Implement photo selection */ },
                onCameraClick = { /* TODO: Implement camera capture */ },
                onMemoryClick = { /* TODO: Implement memory function */ },
                onAudioClick = { /* TODO: Implement audio recording */ },
                onFileClick = { /* TODO: Implement file upload */ },
                onScreenClick = { /* TODO: Implement screenshot */ },
                onNotificationClick = { /* TODO: Implement notification access */ },
                onLocationClick = { /* TODO: Implement location access */ }
            )

            SettingsPanel(
                visible = showSettingsPanel,
                onDismiss = { state.setSettingsPanel(false) },
                memoryMode = memoryMode,
                onMemoryModeChange = { mode ->
                    memoryMode = mode
                    sharedPrefs.edit().putString("memory_mode", mode).apply()
                },
                memoryAttached = memoryAttached,
                onMemoryAttachedChange = { attached ->
                    memoryAttached = attached
                    sharedPrefs.edit().putBoolean("memory_attached", attached).apply()
                },
                manualMemoryUpdate = manualMemoryUpdate,
                onManualMemoryUpdateChange = { manual ->
                    manualMemoryUpdate = manual
                    sharedPrefs.edit().putBoolean("manual_memory_update", manual).apply()
                },
                autoRead = autoRead,
                onAutoReadChange = { read ->
                    autoRead = read
                    sharedPrefs.edit().putBoolean("auto_read", read).apply()
                },
                autoApprove = autoApprove,
                onAutoApproveChange = { approve ->
                    autoApprove = approve
                    sharedPrefs.edit().putBoolean("auto_approve", approve).apply()
                },
                disableStream = disableStream,
                onDisableStreamChange = { stream ->
                    disableStream = stream
                    sharedPrefs.edit().putBoolean("disable_stream", stream).apply()
                },
                disableTools = disableTools,
                onDisableToolsChange = { tools ->
                    disableTools = tools
                    sharedPrefs.edit().putBoolean("disable_tools", tools).apply()
                },
                disableUserPreference = disableUserPreference,
                onDisableUserPreferenceChange = { preference ->
                    disableUserPreference = preference
                    sharedPrefs.edit().putBoolean("disable_user_preference", preference).apply()
                },
                disableLatex = disableLatex,
                onDisableLatexChange = { latex ->
                    disableLatex = latex
                    sharedPrefs.edit().putBoolean("disable_latex", latex).apply()
                },
                onToolManagementClick = { /* TODO: Navigate to Tool Management */ }
            )

            IntegratedSidebar(
                isVisible = showIntegratedSidebar,
                onDismiss = { state.setIntegratedSidebar(false) },
                onMenuItemClick = { menuItem ->
                    state.setIntegratedSidebar(false)
                    handleMenuItemClick(menuItem)
                }
            )
        }
    }
}

private fun loadChatMessages(chatIndex: Int) {
    messages.clear()
    val sampleMessages = listOf(
        ChatMessage(sender = "ai", content = "周末愉快，工作的事交给我"),
        ChatMessage(sender = "user", content = "你好，请问有什么可以帮助你的？"),
        ChatMessage(sender = "ai", content = "我可以帮助你完成各种任务，包括代码编写、问题解答、文本生成等。请告诉我你需要什么帮助。")
    )
    messages.addAll(sampleMessages)
}

private fun handleMenuItemClick(menuItem: String) {
    when (menuItem) {
        "AI 对话" -> { /* TODO: Navigate to AI Chat */ }
        "助手配置" -> { /* TODO: Navigate to Assistant Config */ }
        "包管理" -> { /* TODO: Navigate to Package Manager */ }
        "记忆库" -> { /* TODO: Navigate to Memory Library */ }
        "工具箱" -> { /* TODO: Navigate to Toolbox */ }
        "权限授予" -> { /* TODO: Navigate to Permission Grant */ }
        "工作流" -> { /* TODO: Navigate to Workflow */ }
        "设置" -> { /* TODO: Navigate to Settings */ }
        "使用手册" -> { /* TODO: Open User Manual */ }
        "关于" -> { /* TODO: Show About Dialog */ }
        "更新历史" -> { /* TODO: Show Update History */ }
        "积分详情" -> { /* TODO: Navigate to Points Details */ }
        "通用设置" -> { /* TODO: Navigate to General Settings */ }
        "帮助中心" -> { /* TODO: Navigate to Help Center */ }
    }
}

private fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}
