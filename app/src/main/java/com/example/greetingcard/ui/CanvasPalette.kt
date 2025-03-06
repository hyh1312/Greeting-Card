package com.example.greetingcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greetingcard.R
import com.example.greetingcard.data.StickerData
import com.example.greetingcard.viewmodel.DecorationViewModel
import java.util.UUID

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

// 贴纸选择面板
@Composable
fun StickerPalette(viewModel: DecorationViewModel) {
    val stickerList = remember {
        listOf(
            R.drawable.st1,
            R.drawable.st2,
            R.drawable.st3,
            R.drawable.st4,
            R.drawable.st5,
            R.drawable.st6,
            R.drawable.st7,
            R.drawable.st8,
            R.drawable.st9,
            R.drawable.st10
        )
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
                                offset = Offset(0f, 0f), // 初始位置
                                scale = 1f,
                                rotation = 0f
                            )
                        )
                    }
            ) {
                Image(
                    painterResource(resId),
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
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

