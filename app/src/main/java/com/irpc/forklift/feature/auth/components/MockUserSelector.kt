// 📁 feature/auth/components/MockUserSelector.kt
package com.irpc.forklift.feature.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.domain.model.UserProfile

/**
 * 🛠️ Mock User Selector (Dev Only)
 *
 * แสดงรายการ Mock Users ให้เลือก login
 * - SA (Super Admin): เห็นทุกแผนก
 * - Admin: เห็นเฉพาะ scope
 * - Operator: พนักงานขับรถ
 */
@Composable
fun MockUserSelector(
    users: List<UserProfile>,
    onUserSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        users.forEach { user ->
            OutlinedButton(
                onClick = { onUserSelected(user.email) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    Text(
                        text = user.displayName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "${user.position} · ${user.companyName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
