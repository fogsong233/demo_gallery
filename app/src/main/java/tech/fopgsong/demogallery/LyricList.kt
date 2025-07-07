package tech.fopgsong.demogallery

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

data class LyricAnimateState(
    val offset: Animatable<Float, AnimationVector1D>,
    val focusPercentage: Animatable<Float, AnimationVector1D>
)

private val naturalBounceEasing: Easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f)
private val lyricMoveAnimatedSpec = tween<Float>(durationMillis = 700, easing = naturalBounceEasing)
private val heightPct = 1

@Composable
fun LyricList(lyrics: List<LyricInfo>) {
    val focusOffsetToTopPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx() * 0.4 * heightPct
    }
    remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val isAutoScroll = remember { mutableStateOf(true) }
    val befFocusIndex = remember { mutableIntStateOf(-1) };
    val currentFocusIndex = remember { mutableIntStateOf(-1) };
    val lyricsAnimateStates = remember {
        lyrics.map { LyricAnimateState(Animatable(0.9f), Animatable(0f)) }
    }
    val timeAccum = lyrics.map { it.duration }.runningReduce { bef, now -> bef + now }
    var startTimeMillis = remember { mutableStateOf(System.currentTimeMillis().milliseconds) }
    var nowTimeMillis = remember { mutableStateOf(System.currentTimeMillis().milliseconds) }
    LaunchedEffect(Unit) {
        launch {
            while (true) {
                nowTimeMillis.value = System.currentTimeMillis().milliseconds
                val seconds = (nowTimeMillis.value - startTimeMillis.value)
                val nowFocusIndex = timeAccum.binarySearch(seconds).let {
                    if (it < 0) {
                        -it - 2 // because it returns -(index+1)
                    } else {
                        it + 1
                    }
                }
                delay(200L)
                if (currentFocusIndex.intValue != nowFocusIndex) {
                    befFocusIndex.intValue = currentFocusIndex.intValue
                    currentFocusIndex.intValue = nowFocusIndex
                }
            }
        }
        launch {
            snapshotFlow { currentFocusIndex.intValue }.collect { newValue ->
                if (befFocusIndex.intValue >= 0) {
                    launch {
                        lyricsAnimateStates[befFocusIndex.intValue].focusPercentage.animateTo(
                            0f, animationSpec = lyricMoveAnimatedSpec
                        )
                    }

                }
                if (newValue >= 0) {
                    launch {
                        lyricsAnimateStates[newValue].focusPercentage.animateTo(
                            1f, animationSpec = lyricMoveAnimatedSpec
                        )
                    }
                    val itemInfo =
                        listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == (newValue + 1) }
                    val offsetToTop = -focusOffsetToTopPx.toFloat()
                    // visible
                    if (itemInfo != null && isAutoScroll.value) {
                        Log.d("myself", "move ${itemInfo.offset} and $offsetToTop")
                        launch {
                            listState.customAnimatedScrollToItem(
                                newValue + 1, // 1 for spacer
                                offsetToTop,
                                animationSpec = lyricMoveAnimatedSpec
                            )
                        }
                        launch {
                            lyricsAnimateStates.filterIndexed { index, v -> index == newValue }
                                .forEachIndexed { index, state ->
                                    launch {
                                        val downJob = launch {
                                            state.offset.animateTo(
                                                itemInfo.offset + offsetToTop,
                                                animationSpec = lyricMoveAnimatedSpec
                                            )

                                        }
                                        delay(
                                            (lyricMoveAnimatedSpec.durationMillis * 0.2f).toLong()
                                        )
                                        downJob.cancel()
                                        state.offset.animateTo(
                                            0f,
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessLow,
                                                dampingRatio = Spring.DampingRatioLowBouncy
                                            )
                                        )

                                    }

                                }

                        }
                    }


                }
            }
        }
    }
    Box {
        Image(painter = painterResource(R.mipmap.goat),
            contentDescription = "music background",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.5f), blendMode = BlendMode.Darken
            ),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 4f
                    scaleY = 4f
                    rotationZ =
                        (nowTimeMillis.value - startTimeMillis.value).inWholeMilliseconds / 500f
                    translationX =
                        (nowTimeMillis.value - startTimeMillis.value).inWholeMilliseconds / 500f
                }
                .blur(20.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
//                    .height((LocalConfiguration.current.screenHeightDp * 0.8).toInt().dp)
                .padding(start = 30.dp, end = 15.dp)
                .graphicsLayer { alpha = 0.99F }
                .drawWithContent {
                    val colors = listOf(Color.Transparent, Color.Black)
                    drawContent()
                    val rectHeightPx = 100f
                    drawIntoCanvas { canvas ->
                        val paint = Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            xfermode =
                                android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN)
                        }

                        // 顶部渐变遮罩
                        val topShader = android.graphics.LinearGradient(
                            0f,
                            0f,
                            0f,
                            rectHeightPx,
                            0x00FFFFFF,
                            0xFFFFFFFF.toInt(),
                            android.graphics.Shader.TileMode.CLAMP
                        )
                        paint.shader = topShader
                        canvas.nativeCanvas.drawRect(0f, 0f, size.width, 50f, paint)

                        // 底部渐变遮罩
                        val bottomShader = android.graphics.LinearGradient(
                            0f,
                            size.height - rectHeightPx,
                            0f,
                            size.height,
                            0xFFFFFFFF.toInt(),
                            0x00FFFFFF,
                            android.graphics.Shader.TileMode.CLAMP
                        )
                        paint.shader = bottomShader
                        canvas.nativeCanvas.drawRect(
                            0f, size.height - rectHeightPx, size.width, size.height, paint
                        )
                    }
//                        drawRect(
//                            brush = Brush.verticalGradient(colors),
//                            blendMode = BlendMode.DstIn,
//                            size = Size(height = rectHeightPx, width = this.size.width)
//                        )
//                        drawRect(
//                            brush = Brush.verticalGradient(colors.reversed()),
//                            blendMode = BlendMode.DstIn,
//                            size = Size(height = rectHeightPx, width = this.size.width),
//                            topLeft = Offset.Zero.copy(y = this.size.height - rectHeightPx)
//                        )
                }, verticalArrangement = Arrangement.spacedBy(15.dp), state = listState
            ) {
                item {
                    Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * heightPct * 0.5).toInt().dp))
                }
                lyrics.forEachIndexed { index, content ->
                    item(index) {
                        Lyric(content.originText,
                            content.viceText,
                            lyricsAnimateStates[index].focusPercentage.value,
                            modifier = Modifier
                                .graphicsLayer {
                                    transformOrigin = transformOrigin.copy(pivotFractionX = 0f)
                                    scaleX = lerp(
                                        0.9f, 1f, lyricsAnimateStates[index].focusPercentage.value
                                    )
                                    scaleY = lerp(
                                        0.9f, 1f, lyricsAnimateStates[index].focusPercentage.value
                                    )
                                    lerp(0.7f, 1f, lyricsAnimateStates[index].focusPercentage.value)
                                }
                                .blur(
                                    radius = ((1 - lyricsAnimateStates[index].focusPercentage.value) * 2).dp,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                ))
                        with(LocalDensity.current) {
                            Spacer(Modifier.height(lyricsAnimateStates[index].offset.value.toDp()))
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * heightPct * 0.5).toInt().dp))
                }

            }
//            Spacer(Modifier.height(30.dp))
//            HorizontalDivider(
//                thickness = 3.dp,
//                modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp)
//            )
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 70.dp)
//            ) {
//
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                    contentDescription = "play",
//                    modifier = Modifier.size(40.dp)
//                )
//                Icon(
//                    imageVector = Icons.Filled.PlayArrow, contentDescription = "play",
//                    modifier = Modifier.size(50.dp)
//                )
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = "play",
//                    modifier = Modifier.size(40.dp)
//                )
//            }
        }

    }

}

@Composable
fun Lyric(
    originLyricText: String, viceLyricText: String, focusPct: Float,    // 0f~1f
    modifier: Modifier = Modifier
) {
    val mainAlpha = lerp(0.5f, 0.9f, focusPct)
    val viceAlpha = lerp(0.5f, 0.8f, focusPct)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = originLyricText,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alpha(mainAlpha),
            lineHeight = 27.sp,
            style = TextStyle(
                shadow = Shadow(
                    offset = Offset(5f, 5f),
                    blurRadius = 20f,
                    color = Color(0xFFAAAAAA).copy(alpha = focusPct)
                )
            )
        )
        Spacer(Modifier.height(5.dp))
        Text(
            viceLyricText,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.alpha(viceAlpha),
            style = TextStyle(
                shadow = Shadow(
                    offset = Offset(5f, 5f),
                    blurRadius = 20f,
                    color = Color(0xFFAAAAAA).copy(alpha = focusPct)
                )
            )
        )
    }
}

