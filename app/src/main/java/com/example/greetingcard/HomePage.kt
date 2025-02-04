package com.example.greetingcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import javax.xml.transform.Templates


@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Search()
        TV()
        Templates()

    }
}

@Composable
fun Search() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .width(307.dp)
            .height(42.dp)
    ) {
        Image(
            painter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.search)), // 背景图片
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .padding(start = 36.dp)
                .background(Color.Transparent),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            decorationBox = @Composable { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        // 当空字符时, 显示hint
                        if (text.isEmpty())
                            Text(
                                modifier = Modifier.align(Alignment.TopStart),
                                color = Color.Gray,
                                fontSize = 16.sp,
                                text = "搜索你想做的贺卡~",
                            )
                        // 原本输入框的内容
                        innerTextField()
                    }
                }
            }
        )

    }
}

@Composable
fun TV() {
    Image(
        painter = painterResource(R.drawable.tv),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun Templates() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "热门模板",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 24.sp,
//                fontFamily = FontFamily(Font(R.font.ping_fang_sc)),
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                letterSpacing = 0.38.sp,
            )
        )
        Row(
            modifier = Modifier.height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(R.drawable.template),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(R.drawable.template),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )


        }
    }


}