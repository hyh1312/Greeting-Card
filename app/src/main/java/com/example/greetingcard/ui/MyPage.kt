package com.example.greetingcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.R

@Preview
@Composable
fun MyPage() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Profile()
        Cards()
    }
}

@Composable
fun Profile() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.merrychristmas),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        Row(
            Modifier
                .padding(start = 45.dp, end = 96.dp, top = 89.dp, bottom = 94.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = null,
            )
            Spacer(Modifier.padding(12.dp))
            ProfileInfo()
        }

    }
}

@Preview
@Composable
fun ProfileInfo() {
    Box(
        modifier = Modifier
            .width(202.dp)
            .height(74.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .offset(x = 104.dp, y = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = (-2).dp, y = 2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon1),
                    contentDescription = null
                )
            }
        }

        // IP Location Text
        Text(
            text = "IP：江西",
            textAlign = TextAlign.Center,
            color = Color(0xFF8A8787),
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            letterSpacing = (-0.41).sp,
            modifier = Modifier
                .width(55.dp)
                .height(20.dp)
                .offset(x = 118.dp, y = 26.dp)
        )

        // Account Info Text
        Text(
            text = "账号xxxxxxxxxxx",
            color = Color(0xFF8A8686),
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            letterSpacing = (-0.41).sp,
            modifier = Modifier.offset(x = 0.dp, y = 27.dp)
        )

        // Profile Name
        Text(
            text = "薛定谔的猫",
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W500,
            letterSpacing = (-0.41).sp,
            modifier = Modifier
                .width(104.dp)
                .height(27.dp)
                .offset(x = 0.dp, y = 0.dp)
        )

        // Friends Count
        Text(
            text = "好友：23",
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            letterSpacing = (-0.41).sp,
            modifier = Modifier
                .width(58.dp)
                .height(21.dp)
                .offset(x = 0.dp, y = 53.dp)
        )

        // Following Count
        Text(
            text = "关注：34",
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            letterSpacing = (-0.41).sp,
            modifier = Modifier
                .width(58.dp)
                .height(21.dp)
                .offset(x = 72.dp, y = 53.dp)
        )

        // Followers Count
        Text(
            text = "粉丝：29",
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            letterSpacing = (-0.41).sp,
            modifier = Modifier
                .width(58.dp)
                .height(21.dp)
                .offset(x = 144.dp, y = 53.dp)
        )
    }
}

@Preview
@Composable
fun Cards() {
    Box {
        var flag by remember { mutableStateOf(false) }
        SwitchableBackground(
            text1 = "我制作的",
            text2 = "我接收的",
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
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.card1),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.card2),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else { // right
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.card3),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.card4),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}