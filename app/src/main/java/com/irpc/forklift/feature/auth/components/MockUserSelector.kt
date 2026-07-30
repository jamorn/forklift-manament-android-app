// 📁 feature/auth/components/MockUserSelector.kt
package com.irpc.forklift.feature.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.domain.model.UserProfile

/**
 * 🛠️ Mock User Selector (Dev Only)
 *
 * @Composable
 * fun MockUserSelector(
 *     users: List<UserProfile>,
 *     onUserSelected: (String) -> Unit,
 *     modifier: Modifier = Modifier,
 * ) {
 *     Column(modifier = modifier.padding(16.dp)) {
 *         Text("🛠️ DEV MODE", style = MaterialTheme.typography.labelSmall)
 *         Spacer(Modifier.height(8.dp))
 *
 *         users.forEach { user ->
 *             OutlinedButton(
 *                 onClick = { onUserSelected(user.email) },
 *                 modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
 *             ) {
 *                 Text(user.displayName)
 *             }
 *         }
 *     }
 * }
 */
object MockUserSelector {
    // TODO: implement Compose UI
}
