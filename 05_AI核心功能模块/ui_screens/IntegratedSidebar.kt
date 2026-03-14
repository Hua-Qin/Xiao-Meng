package com.ai.assistance.operit.ui.features.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntegratedSidebar(
    isVisible: Boolean,
    userName: String = "User",
    userAvatar: String = "",
    points: String = "14,431",
    selectedMenuItem: String = "包管理",
    onDismiss: () -> Unit = {},
    onMenuItemClick: (String) -> Unit = {}
) {
    val menuItems = remember {
        listOf(
            MenuItemCategory("AI功能", listOf(
                MenuItem("AI 对话", Icons.Default.Chat),
                MenuItem("助手配置", Icons.Default.Person),
                MenuItem("包管理", Icons.Default.Package),
                MenuItem("记忆库", Icons.Default.Memory)
            )),
            MenuItemCategory("工具", listOf(
                MenuItem("工具箱", Icons.Default.Build),
                MenuItem("权限授予", Icons.Default.Security),
                MenuItem("工作流", Icons.Default.AccountTree)
            )),
            MenuItemCategory("系统", listOf(
                MenuItem("设置", Icons.Default.Settings),
                MenuItem("使用手册", Icons.Default.MenuBook),
                MenuItem("关于", Icons.Default.Info),
                MenuItem("更新历史", Icons.Default.History)
            ))
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(onClick = onDismiss)
            )
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = expandHorizontally(expandFrom = androidx.compose.ui.Alignment.End),
            exit = shrinkHorizontally(shrinkTowards = androidx.compose.ui.Alignment.End),
            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterEnd)
        ) {
            Surface(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .clickable(onClick = {}),
                color = ChatColors.sidebarBackground(),
                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    UserInfoSection(
                        userName = userName,
                        userAvatar = userAvatar,
                        points = points,
                        onPointsClick = { onMenuItemClick("积分详情") },
                        onSettingsClick = { onMenuItemClick("通用设置") },
                        onHelpClick = { onMenuItemClick("帮助中心") }
                    )

                    Divider(
                        color = ChatColors.dividerColor(),
                        thickness = DividerThickness,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    MenuSection(
                        menuItems = menuItems,
                        selectedMenuItem = selectedMenuItem,
                        onMenuItemClick = onMenuItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoSection(
    userName: String,
    userAvatar: String,
    points: String,
    onPointsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onDismiss),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.titleMedium,
                color = ChatColors.sidebarText(),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "个人免费版",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = { },
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "升级",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = "暂无订阅计划",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onPointsClick)
                .padding(8.dp)
                .clip(ChatShapes.buttonShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Stars,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "积分",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ChatColors.sidebarText()
                )
            }

            Text(
                text = points,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = ChatColors.sidebarText()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onSettingsClick,
                modifier = Modifier.weight(1f).height(36.dp),
                shape = ChatShapes.buttonShape
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "通用设置",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 11.sp
                )
            }

            OutlinedButton(
                onClick = onHelpClick,
                modifier = Modifier.weight(1f).height(36.dp),
                shape = ChatShapes.buttonShape
            ) {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "帮助中心",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun MenuSection(
    menuItems: List<MenuItemCategory>,
    selectedMenuItem: String,
    onMenuItemClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        menuItems.forEach { category ->
            Text(
                text = category.title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            category.items.forEach { item ->
                val isSelected = item.name == selectedMenuItem

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onMenuItemClick(item.name) })
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else Color.Transparent
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                               else ChatColors.sidebarText(),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                               else ChatColors.sidebarText(),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            if (category != menuItems.last()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

data class MenuItemCategory(
    val title: String,
    val items: List<MenuItem>
)

data class MenuItem(
    val name: String,
    val icon: ImageVector
)
