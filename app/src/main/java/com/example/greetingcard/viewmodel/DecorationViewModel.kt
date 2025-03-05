package com.example.greetingcard.viewmodel

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModel
import com.example.greetingcard.R
import com.example.greetingcard.data.StickerData
import java.io.IOException


// ViewModel
class DecorationViewModel : ViewModel() {
    private val _background = mutableStateOf(R.drawable.bg1)
    val background: State<Int> = _background

    private val _stickers = mutableStateListOf<StickerData>()
    val stickers: List<StickerData> = _stickers

    fun addSticker(sticker: StickerData) {
        _stickers.add(sticker)
    }

    fun updateSticker(id: String, offset: Offset, scale: Float, rotation: Float) {
        val index = _stickers.indexOfFirst { it.id == id }
        if (index != -1) {
            _stickers[index] = _stickers[index].copy(
                offset = offset,
                scale = scale,
                rotation = rotation
            )
        }
    }

    fun changeBackground(resId: Int) {
        _background.value = resId
    }

    fun exportImage(context: Context, rootView: View) {
        val bitmap = rootView.drawToBitmap()

        saveToGallery(context, bitmap)
    }

    private fun saveToGallery(context: Context, bitmap: Bitmap) {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "design_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/YourAppName")
            }
        }

        try {
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )?.let { uri ->
                contentResolver.openOutputStream(uri)?.use { stream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                        throw IOException("Failed to save bitmap")
                    }
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "保存失败: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}


