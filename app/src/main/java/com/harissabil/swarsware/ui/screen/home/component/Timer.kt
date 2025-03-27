package com.harissabil.swarsware.ui.screen.home.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harissabil.swarsware.common.component.AutoSizeText
import com.harissabil.swarsware.common.constant.Status
import com.harissabil.swarsware.ui.theme.SwarSwareTheme
import com.harissabil.swarsware.ui.theme.spacing

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Timer(
    modifier: Modifier = Modifier,
    timeElapsed: Long,
    status: Status,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    val hours = remember(timeElapsed) { timeElapsed.div(60).div(60) }
    val minutes = remember(timeElapsed) { (timeElapsed.div(60)).rem(60) }
    val seconds = remember(timeElapsed) { timeElapsed.rem(60) }

    // Remember the max possible width of the text to ensure consistency
    val maxPossibleText = remember { "00:00:00" }

    // Remember a mutable state for the font size
    var calculatedFontSize by remember { mutableStateOf(TextUnit.Unspecified) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .padding(MaterialTheme.spacing.large),
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val (title, timer) = createRefs()

                Text(
                    modifier = Modifier.constrainAs(title) {
                        bottom.linkTo(timer.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "Listening your\nsurrounding for:",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                // First, use AutoSizeText with the maxPossibleText to calculate font size
                // This is only used when calculatedFontSize is not specified yet
                if (calculatedFontSize == TextUnit.Unspecified) {
                    AutoSizeText(
                        modifier = Modifier.constrainAs(timer) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = maxPossibleText,
                        maxLines = 1,
                        style = MaterialTheme.typography.displayLarge,
                        onTextLayout = { result ->
                            // Store the calculated font size
                            if (calculatedFontSize == TextUnit.Unspecified) {
                                calculatedFontSize = result.layoutInput.style.fontSize
                            }
                        }
                    )
                } else {
                    // Use regular Text with the already calculated font size
                    Text(
                        modifier = Modifier.constrainAs(timer) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = "${"%02d".format(hours)}:${"%02d".format(minutes)}:${"%02d".format(seconds)}",
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = calculatedFontSize),
                        maxLines = 1
                    )
                }
            }
        }

        AnimatedContent(
            targetState = status,
            transitionSpec = {
                fadeIn(animationSpec = tween(250, easing = EaseIn))
                    .togetherWith(
                        fadeOut(animationSpec = tween(250, easing = EaseOut))
                    )
            },
            contentAlignment = Alignment.Center
        ) {
            when (it) {
                Status.IDLE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = onStart) {
                            Text(text = "Start")
                        }
                    }
                }

                Status.PLAYING -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(onClick = onStop) {
                            Text(text = " Stop ")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TimerPreview() {
    var status by remember { mutableStateOf(Status.IDLE) }

    SwarSwareTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Timer(
                    timeElapsed = 200,
                    status = status,
                    onStart = {
                        status = Status.PLAYING
                    },
                    onStop = {
                        status = Status.IDLE
                    },
                )
            }
        }
    }
}