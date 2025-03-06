package com.example.greetingcard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun SwitchableBackground(
    text1: String = "No.1",
    text2: String = "No.2",
    showRight: Boolean = false,
    clickLeft: () -> Unit = {},
    clickRight: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {// 圆角矩形1
        Box(
            modifier = Modifier
                .width(98.dp)
                .height(53.dp)
                .offset(x = 19.dp, y = 0.dp)
                .background(
                    color = if (showRight) Color(0xFFFEFADE) else Color(0xFFFDD83E),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))

        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f),
                onClick = clickLeft
            ) { }
        }

        // 圆角矩形2
        Box(
            modifier = Modifier
                .width(98.dp)
                .height(53.dp)
                .offset(x = 133.dp, y = 0.dp)
                .background(
                    color = if (showRight) Color(0xFFFDD83E) else Color(0xFFFEFADE),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
        )
        {
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f),
                onClick = clickRight
            ) { }
        }

        // 黄色背景
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(531.dp)
                .offset(y = 31.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(24.dp))
                .background(
                    color = Color(0xFFFDD83D), // RGB: 0.99, 0.85, 0.24
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            // 蓝色背景
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(491.dp)
                    .align(Alignment.Center)

                    .background(
                        color = Color(0xFF8ECEDF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            )

            // 白色背景
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(455.dp)
                    .align(Alignment.Center)
                    .offset(y = 36.dp)
                    .background(Color(0xFFFEFADE))
                    .border(1.dp, Color.Black)
            )
        }
        // 擦掉连接处边框
        Box(
            modifier = Modifier
                .width(96.dp)
                .offset(x = if (showRight) 134.dp else 20.dp, y = 28.dp)
                .height(4.dp)
                .background(
                    color = Color(0xFFFDD83D),
                )
        )
        // 贴纸下的小矩形
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .offset(x = if (showRight) 162.dp else 48.dp, y = 32.dp)
                .background(
                    color = Color(0xFF8ECEDF),
                    shape = RoundedCornerShape(17.dp)
                )
        )
        // 文字1
        Text(
            text = text1,
            color = if (showRight) Color(0xFF888888) else Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W500,
            letterSpacing = 0.68.sp,
            modifier = Modifier.offset(x = 48.dp, y = 9.dp)
        )

        // 文字2
        Text(
            text = text2,
            color = if (showRight) Color.Black else Color(0xFF888888),
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W500,
            letterSpacing = 0.68.sp,
            modifier = Modifier.offset(x = 148.dp, y = 9.dp)
        )
    }
}
