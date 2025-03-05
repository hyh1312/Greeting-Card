package com.example.greetingcard

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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greetingcard.viewmodel.DecorationViewModel

@Preview
@Composable
fun AddPage(
    viewModel: DecorationViewModel = viewModel(),
    goBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            MyTopBar(goBack = goBack, viewModel)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(10.dp)
                .verticalScroll(scrollState),
        ) {
            CanvasArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(489.dp),
                viewModel = viewModel
            )
            Spacer(Modifier.padding(20.dp))
            CanvasPalette(viewModel)
        }
    }
}

@Composable
fun MyTopBar(goBack: () -> Unit = {}, viewModel: DecorationViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(goBack)
        ExportButton(viewModel = viewModel)
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
fun ExportButton(viewModel: DecorationViewModel) { // TODO：权限处理
    val context = LocalContext.current
    val rootView = LocalView.current
//    val permissionState = rememberPermissionState(
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )

    Box(
        modifier = Modifier
            .width(53.dp)
            .height(30.dp)
            .background(
                color = Color(0xFF8ECEDF),
                shape = RoundedCornerShape(size = 17.dp)
            )
            .clickable {
//                when {
//                    // Android 10+ 不需要 WRITE_EXTERNAL_STORAGE
//                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
//                        viewModel.exportImage(context, rootView)
//                    }
//                    //低版本需要请求权限
//                    permissionState.hasPermission -> {
//                        viewModel.exportImage(context, rootView)
//                    }
//
//                    else -> {
//                        permissionState.launchPermissionRequest()
//                    }
//                }
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

