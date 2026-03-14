package com.ai.assistance.operit.ui.features.chat

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AIChatState {
    private val _showModelConfigDialog = MutableStateFlow(false)
    val showModelConfigDialog: StateFlow<Boolean> = _showModelConfigDialog.asStateFlow()

    private val _showPlusMenu = MutableStateFlow(false)
    val showPlusMenu: StateFlow<Boolean> = _showPlusMenu.asStateFlow()

    private val _showSettingsPanel = MutableStateFlow(false)
    val showSettingsPanel: StateFlow<Boolean> = _showSettingsPanel.asStateFlow()

    private val _showIntegratedSidebar = MutableStateFlow(false)
    val showIntegratedSidebar: StateFlow<Boolean> = _showIntegratedSidebar.asStateFlow()

    private val _isFullScreenMode = MutableStateFlow(false)
    val isFullScreenMode: StateFlow<Boolean> = _isFullScreenMode.asStateFlow()

    private val _isVoiceInputMode = MutableStateFlow(false)
    val isVoiceInputMode: StateFlow<Boolean> = _isVoiceInputMode.asStateFlow()

    private val _currentChatTitle = MutableStateFlow("")
    val currentChatTitle: StateFlow<String> = _currentChatTitle.asStateFlow()

    private val _currentTime = MutableStateFlow(LocalDateTime.now())
    val currentTime: StateFlow<LocalDateTime> = _currentTime.asStateFlow()

    fun toggleModelConfigDialog() {
        _showModelConfigDialog.value = !_showModelConfigDialog.value
    }

    fun setModelConfigDialog(show: Boolean) {
        _showModelConfigDialog.value = show
    }

    fun togglePlusMenu() {
        _showPlusMenu.value = !_showPlusMenu.value
    }

    fun setPlusMenu(show: Boolean) {
        _showPlusMenu.value = show
    }

    fun toggleSettingsPanel() {
        _showSettingsPanel.value = !_showSettingsPanel.value
    }

    fun setSettingsPanel(show: Boolean) {
        _showSettingsPanel.value = show
    }

    fun toggleIntegratedSidebar() {
        _showIntegratedSidebar.value = !_showIntegratedSidebar.value
    }

    fun setIntegratedSidebar(show: Boolean) {
        _showIntegratedSidebar.value = show
    }

    fun toggleFullScreenMode() {
        _isFullScreenMode.value = !_isFullScreenMode.value
    }

    fun setFullScreenMode(fullScreen: Boolean) {
        _isFullScreenMode.value = fullScreen
    }

    fun toggleVoiceInputMode() {
        _isVoiceInputMode.value = !_isVoiceInputMode.value
    }

    fun setVoiceInputMode(voiceInput: Boolean) {
        _isVoiceInputMode.value = voiceInput
    }

    fun setCurrentChatTitle(title: String) {
        _currentChatTitle.value = title
    }

    fun updateCurrentTime() {
        _currentTime.value = LocalDateTime.now()
    }

    fun getCurrentTimeFormatted(pattern: String = "HH:mm"): String {
        return _currentTime.value.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun reset() {
        _showModelConfigDialog.value = false
        _showPlusMenu.value = false
        _showSettingsPanel.value = false
        _showIntegratedSidebar.value = false
        _isFullScreenMode.value = false
        _isVoiceInputMode.value = false
        _currentChatTitle.value = ""
        _currentTime.value = LocalDateTime.now()
    }
}
