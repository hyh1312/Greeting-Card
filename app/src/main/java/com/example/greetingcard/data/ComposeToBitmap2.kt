package com.example.greetingcard.data

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.greetingcard.R
import com.example.greetingcard.ui.TransformableSticker
import java.util.UUID

data class CaptureState(
    val capture: Boolean = false
)

fun MutableState<CaptureState>.capture() {
    this.value = this.value.copy(capture = true)
}

private fun MutableState<CaptureState>.captureComplete() {
    this.value = this.value.copy(capture = false)
}

@Composable
fun rememberCaptureController(): MutableState<CaptureState> {
    return remember {
        mutableStateOf(CaptureState(capture = false))
    }
}

@Composable
fun ComposeCapture(
    captureController: MutableState<CaptureState> = rememberCaptureController(),
    onSaveBitmap: (Bitmap?) -> Unit,
    content: @Composable () -> Unit
) {
    val bounds = remember {
        mutableStateOf<Rect?>(null)
    }
    //依据状态值 选择是否使用AndroidView进行展示获取截图
    if (captureController.value.capture) {
        CaptureView(
            captureController = captureController,
            onSaveBitmap = onSaveBitmap,
            bounds = bounds,
            content = content
        )
    } else {
        Surface(modifier = Modifier.onGloballyPositioned {
            bounds.value = it.boundsInRoot()
        }, content = content)

    }
}

@Composable
private fun CaptureView(
    captureController: MutableState<CaptureState>,
    bounds: MutableState<Rect?>,
    onSaveBitmap: ((Bitmap?) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    AndroidView(factory = {
        FrameLayout(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                (bounds.value!!.right - bounds.value!!.left).toInt(),
                (bounds.value!!.bottom - bounds.value!!.top).toInt()
            )
            val composeView = ComposeView(it).apply {
                setContent {
                    content()
                }
            }
            drawListener(composeView, this, captureController, onSaveBitmap)
            addView(
                composeView, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    })
}

private fun drawListener(
    composeView: View, viewGroup: ViewGroup,
    captureController: MutableState<CaptureState>,
    onSaveBitmap: ((Bitmap?) -> Unit)? = null,
) {
    val drawListener = object : ViewTreeObserver.OnDrawListener {
        var remove = false
        override fun onDraw() {
            if (composeView.width > 0) {
                if (!remove) {
                    // View 绘制第一帧 开始截图并移除 监听，随后切换截图状态 回到Compose组件
                    remove = true
                    composeView.post {
                        var bitmap = getViewGroupBitmap(viewGroup)
                        // 切换状态 回到Compose
                        captureController.captureComplete()
                        onSaveBitmap?.invoke(bitmap)
                        composeView.viewTreeObserver.removeOnDrawListener(this)
                    }
                }
            }
        }
    }
    composeView.viewTreeObserver.addOnDrawListener(drawListener)
}

private fun getViewGroupBitmap(viewGroup: ViewGroup): Bitmap? {
    val bitmap = Bitmap.createBitmap(viewGroup.width, viewGroup.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    viewGroup.draw(canvas)
    return bitmap
}

@Preview
@Composable
fun TestCapture() {
    //截图控制器
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val captureController = rememberCaptureController()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ComposeCapture(
            captureController = captureController,
            onSaveBitmap = { mp ->
                bitmap = mp
            }) {

            // Test

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "测试截图bitmap")
                Image(
                    painter = painterResource(R.drawable.st1),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                TransformableSticker(
                    StickerData(
                        id = UUID
                            .randomUUID()
                            .toString(),
                        resId = R.drawable.st1,
                        offset = Offset(100f, 100f), // 初始位置
                        scale = 1f,
                        rotation = 0f
                    )
                ) { offset, fl, fl2 ->
                }

            }
        }
        Button(
            onClick = {
                //触发截图
                captureController.capture()

            }
        ) {
            Text(text = "save")
        }
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "Captured Image")
        }
    }
}


