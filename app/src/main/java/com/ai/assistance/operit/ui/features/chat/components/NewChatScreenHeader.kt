package com.ai.assistance.operit.ui.features.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
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

sealed class NavigationItem {
    object Chat : NavigationItem()
    object Tasks : NavigationItem()
    object Skills : NavigationItem()
    object Favorites : NavigationItem()
}

@Composable
fun NewChatScreenHeader(
    modifier: Modifier = Modifier,
    onNavigate: (NavigationItem) -> Unit
) {
    var selectedItem by remember { mutableStateOf(NavigationItem.Chat) }
    
    Column(modifier = modifier.fillMaxWidth()) {
        // 顶部导航栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 左侧品牌标识
            Text(
                text = "扣子",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E40AF), // 深蓝色
                style = MaterialTheme.typography.headlineSmall
            )
            
            // 右侧功能入口
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationButton(
                    icon = Icons.Default.Task,
                    label = stringResource(R.string.tasks),
                    isSelected = selectedItem == NavigationItem.Tasks,
                    onClick = {
                        selectedItem = NavigationItem.Tasks
                        onNavigate(NavigationItem.Tasks)
                    }
                )
                
                NavigationButton(
                    icon = Icons.Default.Work,
                    label = stringResource(R.string.skills),
                    isSelected = selectedItem == NavigationItem.Skills,
                    onClick = {
                        selectedItem = NavigationItem.Skills
                        onNavigate(NavigationItem.Skills)
                    }
                )
                
                NavigationButton(
                    icon = Icons.Default.Star,
                    label = stringResource(R.string.favorites),
                    isSelected = selectedItem == NavigationItem.Favorites,
                    onClick = {
                        selectedItem = NavigationItem.Favorites
                        onNavigate(NavigationItem.Favorites)
                    }
                )
            }
        }
        
        // 选中状态指示线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            when (selectedItem) {
                NavigationItem.Chat -> {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color(0xFF1E40AF))
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                }
                NavigationItem.Tasks -> {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color(0xFF1E40AF))
                            .align(Alignment.CenterEnd)
                            .padding(end = 140.dp)
                    )
                }
                NavigationItem.Skills -> {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color(0xFF1E40AF))
                            .align(Alignment.CenterEnd)
                            .padding(end = 80.dp)
                    )
                }
                NavigationItem.Favorites -> {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color(0xFF1E40AF))
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFF1E40AF) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFF1E40AF) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
