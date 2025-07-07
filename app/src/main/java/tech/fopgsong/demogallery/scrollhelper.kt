package tech.fopgsong.demogallery

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState

suspend fun LazyListState.customAnimatedScrollToItem(
    targetIndex: Int,
    offsetToTop: Float,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 500),
) {
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == targetIndex }

    if (itemInfo != null) {
        // 如果 item 已经可见，只需要 scrollBy 它的相对位置
        val offset = itemInfo.offset
        Log.d("myself", "offset is $offset")
        animateScrollBy(
            value = offset.toFloat() + offsetToTop, animationSpec = animationSpec
        )
    } else {
        animateScrollToItem(index = targetIndex, scrollOffset = offsetToTop.toInt())
//        withFrameNanos {}
//
//        val newInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == targetIndex }
//        if (newInfo != null) {
//            animateScrollBy(
//                value = newInfo.offset.toFloat(), animationSpec = animationSpec
//            )
//        }
    }
}
