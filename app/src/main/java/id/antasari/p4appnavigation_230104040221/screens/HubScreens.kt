package id.antasari.p4appnavigation_230104040221.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import id.antasari.p4appnavigation_230104040221.nav.Route

sealed class HubTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Dashboard : HubTab("Dashboard", Icons.Outlined.Dashboard)
    data object Messages : HubTab("Messages", Icons.Outlined.Chat)
    data object Profile : HubTab("Profile", Icons.Outlined.AccountCircle)
}

@Composable
fun HubScreen(nav: NavHostController, tab: HubTab) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tab is HubTab.Dashboard,
                    onClick = {
                        nav.navigate(Route.HubDashboard.path) {
                            popUpTo(Route.Hub.path) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(HubTab.Dashboard.icon, null) },
                    label = { Text(HubTab.Dashboard.label) }
                )
                NavigationBarItem(
                    selected = tab is HubTab.Messages,
                    onClick = {
                        nav.navigate(Route.HubMessages.path) {
                            popUpTo(Route.Hub.path) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(HubTab.Messages.icon, null) },
                    label = { Text(HubTab.Messages.label) }
                )
                NavigationBarItem(
                    selected = tab is HubTab.Profile,
                    onClick = {
                        nav.navigate(Route.HubProfile.path) {
                            popUpTo(Route.Hub.path) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(HubTab.Profile.icon, null) },
                    label = { Text(HubTab.Profile.label) }
                )
            }
        }
    ) { padding ->
        when (tab) {
            is HubTab.Dashboard -> DashboardTab(Modifier.padding(padding))
            is HubTab.Messages -> MessagesTab(Modifier.padding(padding)) {
                nav.navigate(Route.HubMsgDetail.path)
            }
            is HubTab.Profile -> ProfileTab(Modifier.padding(padding))
        }
    }
}

@Composable
private fun DashboardTab(mod: Modifier) {
    Column(mod.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Dashboard Fragment", style = MaterialTheme.typography.titleMedium)
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Welcome!", fontWeight = FontWeight.SemiBold)
                Text("This screen represents a dashboard inside a single Activity hosting multiple fragments/tabs.")
            }
        }
        InfoCard("Hints", listOf(
            "Each tab maps to a fragment-like screen",
            "Bottom navigation switches tabs within the same Activity",
            "Back preserves tab state unless the stack is cleared"
        ))
    }
}

@Composable
private fun MessagesTab(mod: Modifier, onOpenDetail: () -> Unit) {
    Column(mod.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Messages Fragment", style = MaterialTheme.typography.titleMedium)
        listOf(
            Triple("Android System", "Welcome to Navigation Lab!", "2m"),
            Triple("Compose Tips", "Use Scaffold + TopAppBar...", "1h"),
            Triple("Release Notes", "Material 3 components...", "ytd")
        ).forEach { (sender, msg, time) ->
            ElevatedCard(Modifier.fillMaxWidth().clickable { onOpenDetail() }) {
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.Chat, null) },
                    headlineContent = { Text(sender) },
                    supportingContent = { Text(msg) },
                    trailingContent = { Text(time) }
                )
            }
        }
    }
}

@Composable
fun MessageDetailScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.Chat, null) },
                    headlineContent = { Text("Fragment Navigation") },
                    supportingContent = { Text("This is a detail screen opened from the Messages tab.") }
                )
                CodeBlock("""
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DetailFragment())
                        .addToBackStack(null).commit();
                """.trimIndent())
                OutlinedButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
                    Text("Back")
                }
            }
        }
    }
}

@Composable
private fun ProfileTab(mod: Modifier) {
    Column(mod.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Profile Fragment", style = MaterialTheme.typography.titleMedium)
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.AccountCircle, null) },
                    headlineContent = { Text("Dina Muzaina Aqillah") },
                    supportingContent = { Text("Information Technology Student") }
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text("Demos Completed") })
                    AssistChip(onClick = {}, label = { Text("4/4") })
                }
            }
        }
    }
}

@Composable
private fun CodeBlock(text: String) {
    Surface(tonalElevation = 2.dp, shape = MaterialTheme.shapes.medium) {
        Text(text, modifier = Modifier.fillMaxWidth().padding(12.dp))
    }
}

@Composable
private fun InfoCard(title: String, bullets: List<String>) {
    Card {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            bullets.forEach { Text("• $it") }
        }
    }
}