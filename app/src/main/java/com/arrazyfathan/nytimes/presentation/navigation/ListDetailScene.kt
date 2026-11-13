package com.arrazyfathan.nytimes.presentation.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.contains
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import com.arrazyfathan.nytimes.designsystem.theme.DomineBold
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular

private const val EXPANDED_WINDOW_WIDTH_DP = 600
private const val DETAIL_FADE_DURATION_MS = 200

/**
 * A [Scene] that displays a list pane on the left and a detail pane (or empty placeholder)
 * on the right when running on foldable phones (unfolded) or tablets (>= 600dp width).
 */
data class ListDetailScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntry: NavEntry<T>?,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOfNotNull(listEntry, detailEntry)

    override val content: @Composable (() -> Unit) = {
        Row(
            modifier = Modifier.fillMaxSize().background(Color.White),
        ) {
            Box(
                modifier = Modifier.weight(0.45f).fillMaxHeight(),
            ) {
                listEntry.Content()
            }

            Box(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                        .background(Color.Black.copy(alpha = 0.12f)),
            )

            Box(
                modifier = Modifier.weight(0.55f).fillMaxHeight().background(Color.White),
            ) {
                AnimatedContent(
                    targetState = detailEntry,
                    contentKey = { entry -> entry?.contentKey ?: "detail_placeholder" },
                    transitionSpec = {
                        fadeIn(animationSpec = tween(DETAIL_FADE_DURATION_MS)) togetherWith
                            fadeOut(
                                animationSpec = tween(DETAIL_FADE_DURATION_MS),
                            )
                    },
                    label = "DetailPaneAnimatedContent",
                ) { entry ->
                    if (entry != null) {
                        entry.Content()
                    } else {
                        DetailPlaceholder()
                    }
                }
            }
        }
    }

    companion object {
        fun listPane() =
            metadata {
                put(ListKey, true)
            }

        fun detailPane() =
            metadata {
                put(DetailKey, true)
            }
    }

    object ListKey : NavMetadataKey<Boolean>

    object DetailKey : NavMetadataKey<Boolean>
}

@Composable
private fun DetailPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "The New York Times",
            fontSize = 20.sp,
            fontFamily = DomineBold,
            color = Color.Black.copy(alpha = 0.35f),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select an article to read its details",
            fontSize = 14.sp,
            fontFamily = NotoSansRegular,
            color = Color.Gray,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun <T : Any> rememberListDetailSceneStrategy(): ListDetailSceneStrategy<T> {
    val windowWidthDp =
        with(LocalDensity.current) {
            LocalWindowInfo.current.containerSize.width
                .toDp()
        }
    val isExpandedWidth = windowWidthDp >= EXPANDED_WINDOW_WIDTH_DP.dp

    return remember(isExpandedWidth) {
        ListDetailSceneStrategy(isExpandedWidth = isExpandedWidth)
    }
}

class ListDetailSceneStrategy<T : Any>(
    private val isExpandedWidth: Boolean,
) : SceneStrategy<T> {
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        if (!isExpandedWidth) return null

        val lastEntry = entries.lastOrNull() ?: return null

        if (lastEntry.metadata.contains(ListDetailScene.DetailKey)) {
            val listEntry =
                entries.findLast { it.metadata.contains(ListDetailScene.ListKey) } ?: return null
            return ListDetailScene(
                key = listEntry.contentKey,
                previousEntries = entries.dropLast(1),
                listEntry = listEntry,
                detailEntry = lastEntry,
            )
        }

        if (lastEntry.metadata.contains(ListDetailScene.ListKey)) {
            return ListDetailScene(
                key = lastEntry.contentKey,
                previousEntries = entries.dropLast(1),
                listEntry = lastEntry,
                detailEntry = null,
            )
        }

        return null
    }
}
