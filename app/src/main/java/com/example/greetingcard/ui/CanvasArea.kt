package com.example.greetingcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.example.greetingcard.R
import com.example.greetingcard.data.StickerData
import com.example.greetingcard.viewmodel.DecorationViewModel

// 画布
@Composable
fun CanvasArea(
    modifier: Modifier = Modifier,
    viewModel: DecorationViewModel
) {
    val currentBackground by viewModel.background
    val stickers = viewModel.stickers

    Box(
        modifier = modifier.background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // 背景
        Image(
            painter = painterResource(currentBackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 树
        Image(
            painter = painterResource(R.drawable.tree),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .offset(y = 50.dp),
            contentScale = ContentScale.Fit
        )

        // 贴纸
        stickers.forEach { sticker ->
            TransformableSticker(
                sticker = sticker,
                onTransform = { offsetChange, scaleChange, rotationChange ->
                    viewModel.updateSticker(
                        sticker.id,
                        offset = sticker.offset + offsetChange,
                        scale = sticker.scale * scaleChange,
                        rotation = sticker.rotation + rotationChange
                    )
                }
            )
        }
    }
}

// 可变换的贴纸组件
@Composable
fun TransformableSticker(
    sticker: StickerData,
    onTransform: (Offset, Float, Float) -> Unit
) {
    var offset by remember { mutableStateOf(sticker.offset) }
    var scale by remember { mutableStateOf(sticker.scale) }
    var rotation by remember { mutableStateOf(sticker.rotation) }

    Box(
        modifier = Modifier
            .offset { offset.round() }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = false
                ) { centroid, pan, zoom, rotate ->
                    offset += pan * (1f / scale)
                    scale *= zoom
                    rotation += rotate
                    onTransform(pan, zoom, rotate)
                }
            }

    ) {
        Image(
            painter = painterResource(sticker.resId),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}
