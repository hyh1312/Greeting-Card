package com.example.greetingcard.data

import androidx.compose.ui.geometry.Offset

// 贴图类
data class StickerData(
    val id: String,
    val resId: Int,
    val offset: Offset,
    val scale: Float,
    val rotation: Float
)