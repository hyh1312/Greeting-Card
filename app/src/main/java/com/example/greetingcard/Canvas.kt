package com.example.greetingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import coil.compose.AsyncImage
import com.example.greetingcard.data.StickerData
import com.example.greetingcard.viewmodel.DecorationViewModel
import java.util.UUID


// 画布
@Composable
fun CanvasArea(
    modifier: Modifier = Modifier,
    viewModel: DecorationViewModel
) {
    val currentBackground by viewModel.background
    val stickers = viewModel.stickers
    var selectedSticker by remember { mutableStateOf<StickerData?>(null) }

    Box(
        modifier = modifier.background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // 背景
        AsyncImage(
            model = currentBackground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 树
        AsyncImage(
            model = R.drawable.tree,
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
                isSelected = selectedSticker?.id == sticker.id,
                onSelected = { selectedSticker = sticker },
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
    isSelected: Boolean,
    onSelected: () -> Unit,
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
            // 好丑的边框...
            // .border(if (isSelected) 2.dp else 0.dp, Color.Blue)
            .clickable { onSelected() }
    ) {
        AsyncImage(
            model = sticker.resId,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}


// 贴纸选择面板
@Composable
fun StickerPalette(viewModel: DecorationViewModel) {
    val stickerList = remember {
        listOf(R.drawable.st1, R.drawable.st2, R.drawable.st3, R.drawable.st4, R.drawable.st5)
    }

    LazyRow {
        items(stickerList) { resId ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
                    .clickable {
                        viewModel.addSticker(
                            StickerData(
                                id = UUID
                                    .randomUUID()
                                    .toString(),
                                resId = resId,
                                offset = Offset(100f, 100f), // 初始位置
                                scale = 1f,
                                rotation = 0f
                            )
                        )
                    }
            ) {
                AsyncImage(
                    model = resId,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

// 背景选择面板
@Composable
fun BackgroundPalette(viewModel: DecorationViewModel) {
    val backgroundList = remember {
        listOf(R.drawable.bg1, R.drawable.bg2, R.drawable.bg3)
    }

    LazyRow {
        items(backgroundList) { resId ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(4.dp)
                    .clickable { viewModel.changeBackground(resId) }
            ) {
                AsyncImage(
                    model = resId,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun CanvasPalette(viewModel: DecorationViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        var flag by remember { mutableStateOf(false) }
        SwitchableBackground(
            text1 = "贴纸",
            text2 = "背景外框",
            flag,
            { flag = false },
            { flag = true })
        Box(
            modifier = Modifier
                .width(361.dp)
                .height(455.dp)
                .offset(y = 92.dp)
                .padding(24.dp)
        ) {
            if (!flag) { // left
                StickerPalette(viewModel)
            } else { // right
                BackgroundPalette(viewModel)
            }
        }
    }
}