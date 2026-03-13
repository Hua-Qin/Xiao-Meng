package com.ai.assistance.operit.ui.features.chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.animateEnterExit
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Workflow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.assistance.operit.R
import java.util.*

@Composable
fun GreetingAndToolsSection(
    modifier: Modifier = Modifier,
    onToolClick: (ToolCard) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // 动态问候语
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Sun icon",
                tint = Color(0xFFF59E0B), // 太阳黄色
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = getGreetingMessage(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        // 工具卡片区域
        ToolCardSection(
            tools = getToolCards(),
            onToolClick = onToolClick,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun ToolCardSection(
    tools: List<ToolCard>,
    onToolClick: (ToolCard) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(tools) {
            ToolCardItem(
                tool = it,
                onClick = { onToolClick(it) }
            )
        }
    }
}

@Composable
private fun ToolCardItem(
    tool: ToolCard,
    onClick: () -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    
    AnimatedVisibility(
        visible = !isRemoved,
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + 
                scaleOut(animationSpec = tween(durationMillis = 300), targetScale = 0.8f)
    ) {
        Card(
            modifier = Modifier
                .width(100.dp)
                .clickable(onClick = onClick)
                .animateEnterExit(
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)) + 
                            scaleIn(animationSpec = tween(durationMillis = 300), initialScale = 0.8f)
                ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(tool.iconBackground, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tool.icon,
                        contentDescription = tool.title,
                        tint = tool.iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = tool.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 2
                )
                
                // 关闭按钮
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(20.dp)
                        .clickable {
                            isRemoved = true
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Remove tool",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private fun getGreetingMessage(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour in 6..11 -> "早上好！有哪些工作需要处理？"
        hour in 12..17 -> "中午好！有哪些工作需要处理？"
        else -> "晚上好！有哪些工作需要处理？"
    }
}

private fun getToolCards(): List<ToolCard> {
    return listOf(
        ToolCard(
            title = "代码运行器",
            icon = Icons.Default.Code,
            iconBackground = Color(0xFFE0F2FE),
            iconColor = Color(0xFF0284C7)
        ),
        ToolCard(
            title = "文件转换",
            icon = Icons.Default.FileCopy,
            iconBackground = Color(0xFFECFCCB),
            iconColor = Color(0xFF65A30D)
        ),
        ToolCard(
            title = "网络搜索",
            icon = Icons.Default.Search,
            iconBackground = Color(0xFFFCE7F3),
            iconColor = Color(0xFFDB2777)
        ),
        ToolCard(
            title = "系统工具",
            icon = Icons.Default.Settings,
            iconBackground = Color(0xFFF3E8FF),
            iconColor = Color(0xFF9333EA)
        ),
        ToolCard(
            title = "工作流",
            icon = Icons.Default.Workflow,
            iconBackground = Color(0xFFDBEAFE),
            iconColor = Color(0xFF2563EB)
        )
    )
}

data class ToolCard(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconBackground: Color,
    val iconColor: Color
)
