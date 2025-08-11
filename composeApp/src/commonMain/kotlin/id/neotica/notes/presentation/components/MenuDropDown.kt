package id.neotica.notes.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuDropDown(item: List<DotsMenuItem>, onLongClick: () -> Unit? = {}) {
    val expanded = remember { mutableStateOf(false) }
    Box {
        Text(
            text = "More",
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        expanded.value = !expanded.value
                    },
                    onLongClick = { onLongClick() },
                )
        )
//        Icon(
//            modifier = Modifier
//                .minimumInteractiveComponentSize()
//                .combinedClickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = ripple(bounded = true, color = White),
//                    onClick = {
//                        expanded.value = !expanded.value
//                    },
//                    onLongClick = { onLongClick() },
//                ),
//            painter = painterResource(three_dots),
//            contentDescription = "",
//            tint = White
//        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
//            containerColor = DarkPrimaryTransparent2,
        ) {
            item.forEach {
                DropdownItem(it.title, White, it.custom) {
                    expanded.value = false
                    it.action()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropdownItem(
    title: String,
    textColor: Color = Unspecified,
    custom: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = Modifier
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = White),
                onClick = onClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
            )
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (custom != null) custom() else {
            Text(
                text = title,
                color = textColor
            )
        }
    }
}

data class DotsMenuItem(
    val title: String,
    val custom: (@Composable () -> Unit)? = null,
    val action: () -> Unit
)