// 📁 feature/checklist/components/CopyForwardBanner.kt
package com.irpc.forklift.feature.checklist.components

/**
 * 🔄 Copy-Forward Banner
 *
 * แสดงข้อความ "อ้างอิงจากกะ X วันที่ Y" ที่ด้านบนฟอร์ม
 *
 * @Composable
 * fun CopyForwardBanner(
 *     date: String,
 *     shift: String,
 * ) {
 *     Surface(
 *         color = MaterialTheme.colorScheme.primaryContainer,
 *         shape = RoundedCornerShape(8.dp),
 *     ) {
 *         Row(modifier = Modifier.padding(12.dp)) {
 *             Icon(Icons.AutoMirrored.Default.Replay, ...)
 *             Text("อ้างอิงจากกะ $shift วันที่ $date")
 *         }
 *     }
 * }
 */
object CopyForwardBanner {
    // TODO: implement Compose UI
}
