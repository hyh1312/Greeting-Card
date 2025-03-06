package com.example.greetingcard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greetingcard.R
import com.example.greetingcard.data.ComposeCapture
import com.example.greetingcard.data.capture
import com.example.greetingcard.data.rememberCaptureController
import com.example.greetingcard.viewmodel.DecorationViewModel

@Preview
@Composable
fun AddPage(
    viewModel: DecorationViewModel = viewModel(),
    goBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val captureController = rememberCaptureController()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            MyTopBar(goBack = goBack) {
                captureController.capture();
                viewModel.exportBitmap(context)
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(10.dp)
                .verticalScroll(scrollState),
        ) {
            ComposeCapture(
                captureController = captureController,
                onSaveBitmap = { mp ->
                    viewModel.bitmap = mp
                }) {
                CanvasArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(489.dp),
                    viewModel = viewModel
                )
            }

            /*Button(
                onClick = {
                    //触发截图
                    captureController.capture()
                }
            ) {
                if (bitmap == null) Text(text = "save")
                else Text(text = "SAVED!!")
            }*/

            Spacer(Modifier.padding(20.dp))

            CanvasPalette(viewModel)
        }
    }
}

@Composable
fun MyTopBar(goBack: () -> Unit = {}, export: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(goBack)
        ExportButton(export)
    }
}

@Preview
@Composable
fun BackButton(goBack: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(Color.Transparent) // 透明背景
            .clip(CircleShape)
            .clickable(onClick = goBack) // 点击事件
            .padding(0.dp), // 内边距
        contentAlignment = Alignment.Center // 内容居中
    ) {

        Icon(
            ImageVector.vectorResource(id = R.drawable.back),
            contentDescription = "Add",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ExportButton(export: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .width(53.dp)
            .height(30.dp)
            .background(
                color = Color(0xFF8ECEDF),
                shape = RoundedCornerShape(size = 17.dp)
            )
            .clickable {
                export()
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "保存",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.28.sp,
            )
        )
    }
}


