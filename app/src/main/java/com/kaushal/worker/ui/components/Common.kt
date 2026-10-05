package com.kaushal.worker.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kaushal.worker.R
import com.kaushal.worker.ui.theme.*

@Composable
fun KaushalButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(56.dp).fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = KaushalOrange,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFB8C0CA)
        )
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OutlinedKaushalButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp).fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = KaushalNavy)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun KaushalTextField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, singleLine: Boolean = true) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = KaushalOrange,
            focusedLabelColor = KaushalOrange,
            unfocusedBorderColor = KaushalBorder
        )
    )
}

@Composable
fun ScreenTopBar(title: String, onBack: (() -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = KaushalNavy) }
        } else {
            Spacer(Modifier.width(8.dp))
        }
        Text(title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
        Row(content = actions)
    }
}

@Composable
fun ProfileAvatar(
    photoUri: String?,
    size: Dp = 48.dp,
    onClick: (() -> Unit)? = null,
    showAddBadge: Boolean = false,
    modifier: Modifier = Modifier
) {
    val clickableModifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier

    Box(
        modifier = clickableModifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (!photoUri.isNullOrBlank()) {
            AsyncImage(
                model = photoUri,
                contentDescription = "Profile Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, KaushalOrange, CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFFFE9D6))
                    .border(1.5.dp, KaushalOrange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = KaushalNavy,
                    modifier = Modifier.fillMaxSize(0.6f)
                )
            }
        }

        if (showAddBadge) {
            Box(
                modifier = Modifier
                    .size(size * 0.32f)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(KaushalOrange),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Photo",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize(0.7f)
                )
            }
        }
    }
}

@Composable
fun IndustrialBackdrop(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(0.dp))
            .background(
                androidx.compose.ui.graphics.Brush.verticalGradient(
                    listOf(Color(0xFFF5F9FF), Color(0xFFFFF2E6))
                )
            )
    ) {
        Text("⛏️   🏗️   🏭", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.headlineMedium)
        Text(
            "Industrial background placeholder — replace with final KAUSHAL artwork",
            modifier = Modifier.align(Alignment.BottomCenter).padding(10.dp),
            style = MaterialTheme.typography.bodySmall,
            color = KaushalMuted
        )
    }
}

@Composable
fun WorkerHero(title: String? = null, subtitle: String? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(150.dp).clip(CircleShape).background(Color(0xFFFFE9D6)),
            contentAlignment = Alignment.Center
        ) {
            Text("👷", style = MaterialTheme.typography.displayLarge)
        }
        if (title != null) Text(title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
        if (subtitle != null) Text(subtitle, color = KaushalMuted, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun SelectionRow(title: String, subtitle: String? = null, selected: Boolean, onClick: () -> Unit, leading: String = "○") {
    val border = if (selected) KaushalOrange else KaushalBorder
    val bg = if (selected) Color(0xFFFFF4EA) else Color.White
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(17.dp)).background(bg)
            .border(1.5.dp, border, RoundedCornerShape(17.dp)).clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(leading, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = if (selected) KaushalOrangeDark else KaushalNavy)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.bodySmall, color = KaushalMuted)
        }
        RadioButton(selected = selected, onClick = onClick, colors = RadioButtonDefaults.colors(selectedColor = KaushalOrange))
    }
}

private data class NavItem(
    val key: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
)

@Composable
fun BottomNavigationBar(current: String, onNavigate: (String) -> Unit) {
    val items = listOf(
        NavItem("Home", R.string.nav_home, Icons.Default.Home),
        NavItem("Learn", R.string.nav_learn, Icons.AutoMirrored.Filled.MenuBook),
        NavItem("Progress", R.string.nav_progress, Icons.Default.BarChart),
        NavItem("Profile", R.string.nav_profile, Icons.Default.Person)
    )

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            val selected = current == item.key
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.key) },
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = { Text(stringResource(item.labelResId), fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = KaushalOrange,
                    selectedTextColor = KaushalOrange,
                    indicatorColor = Color(0xFFFFF0E5),
                    unselectedIconColor = Color(0xFF718096),
                    unselectedTextColor = Color(0xFF718096)
                )
            )
        }
    }
}

@Composable
fun ModuleCard(title: String, description: String, icon: String, hasAr: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
            .background(Color.White).border(1.dp, KaushalBorder, RoundedCornerShape(18.dp))
            .clickable { onClick() }.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFFFF0E3)), contentAlignment = Alignment.Center) {
            Text(icon)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = KaushalNavy)
            Text(description, style = MaterialTheme.typography.bodySmall, color = KaushalMuted, maxLines = 2)
        }
        Column(horizontalAlignment = Alignment.End) {
            if (hasAr) Text("AR", color = KaushalOrange, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ChevronRight, null, tint = KaushalMuted)
        }
    }
}
