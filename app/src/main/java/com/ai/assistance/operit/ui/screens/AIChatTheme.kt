package com.ai.assistance.operit.ui.features.chat

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val ChatTopBarHeight = 56.dp
val ChatBottomBarHeight = 80.dp
val MessageBubbleMaxWidth = 0.8f
val MessageSpacing = 8.dp
val MessagePadding = 12.dp
val InputFieldHeight = 56.dp
val InputFieldCornerRadius = 28.dp
val DialogCornerRadius = 16.dp
val SidebarWidth = 280.dp
val IconButtonSize = 40.dp
val SwitchTrackWidth = 44.dp
val SwitchTrackHeight = 24.dp
val DividerThickness = 1.dp

@Composable
fun AIChatTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

object ChatColors {
    @Composable
    fun userMessageBackground() = MaterialTheme.colorScheme.primaryContainer
    
    @Composable
    fun userMessageText() = MaterialTheme.colorScheme.onPrimaryContainer
    
    @Composable
    fun aiMessageBackground() = MaterialTheme.colorScheme.surface
    
    @Composable
    fun aiMessageText() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun systemMessageBackground() = MaterialTheme.colorScheme.surfaceVariant
    
    @Composable
    fun systemMessageText() = MaterialTheme.colorScheme.onSurfaceVariant
    
    @Composable
    fun topBarBackground() = MaterialTheme.colorScheme.surface
    
    @Composable
    fun topBarTitle() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun inputFieldBackground() = MaterialTheme.colorScheme.surface
    
    @Composable
    fun inputFieldText() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun dialogBackground() = MaterialTheme.colorScheme.surface
    
    @Composable
    fun dialogTitle() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun dialogText() = MaterialTheme.colorScheme.onSurfaceVariant
    
    @Composable
    fun sidebarBackground() = MaterialTheme.colorScheme.surface
    
    @Composable
    fun sidebarText() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun iconButtonTint() = MaterialTheme.colorScheme.onSurface
    
    @Composable
    fun textButtonTint() = MaterialTheme.colorScheme.primary
    
    @Composable
    fun dividerColor() = MaterialTheme.colorScheme.outlineVariant
}

object ChatShapes {
    val messageBubbleShape = RoundedCornerShape(16.dp)
    val userMessageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    val aiMessageShape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    val inputFieldShape = RoundedCornerShape(InputFieldCornerRadius)
    val dialogShape = RoundedCornerShape(DialogCornerRadius)
    val buttonShape = RoundedCornerShape(8.dp)
    val iconButtonShape = RoundedCornerShape(IconButtonSize / 2)
}

object ChatTypography {
    @Composable
    fun topBarTitle() = MaterialTheme.typography.titleMedium
    
    @Composable
    fun messageText() = MaterialTheme.typography.bodyLarge
    
    @Composable
    fun systemMessageText() = MaterialTheme.typography.bodySmall
    
    @Composable
    fun inputFieldText() = MaterialTheme.typography.bodyLarge
    
    @Composable
    fun dialogTitle() = MaterialTheme.typography.headlineSmall
    
    @Composable
    fun dialogText() = MaterialTheme.typography.bodyMedium
    
    @Composable
    fun buttonText() = MaterialTheme.typography.labelLarge
}
