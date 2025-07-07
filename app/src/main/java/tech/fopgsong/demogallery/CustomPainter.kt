//package tech.fopgsong.demogallery
//
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.IntSize
//import androidx.compose.ui.unit.toSize
//
//class TransformImagePainter constructor(
//    private val image: ImageBitmap,
//    private val rotateAngle: Int,
//    private val offset: Offset,
//    private val origin: Offset,
//    private val imageSize: IntSize = IntSize(image.width, image.height)
//) : Painter() {
//    private val size: IntSize = validateSize(imageSize)
//    override fun DrawScope.onDraw() {
//        image.
//        drawImage(
//            image,
//            topLeft = offset
//        )
//    }
//
//    override val intrinsicSize: Size get() = size.toSize()
//
//    private fun validateSize(srcSize: IntSize): IntSize {
//        require(
//                    srcSize.width >= 0 &&
//                    srcSize.height >= 0 &&
//                    srcSize.width <= image.width &&
//                    srcSize.height <= image.height
//        )
//        return srcSize
//    }
//
//}
//}