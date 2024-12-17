package com.scode.swipeablepagescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.scode.swipeablepagescomposeapp.ui.theme.SwipeablePagesComposeAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animals = listOf(
            R.drawable.cat,
            R.drawable.chicken,
            R.drawable.dog,
        )
        setContent {
            SwipeablePagesComposeAppTheme {
                val pagerState = rememberPagerState(initialPage = 0, pageCount = { animals.size })
                val scope = rememberCoroutineScope()

                Box(modifier = Modifier.fillMaxSize()) {
                    // Use items directly instead of pageCount
                    HorizontalPager(
                        state = pagerState,
                        key = { animals[it] },
                        pageSize = PageSize.Fill
                    ) { index ->
                        Image(
                            painter = painterResource(id = animals[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Navigation Buttons
                    Box(
                        modifier = Modifier
                            .offset(y = -(16).dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .background(colorResource(id = R.color.white))
                            .padding(8.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    val targetPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                                    pagerState.animateScrollToPage(targetPage)
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Go back",
                                tint = Color.Black
                            )
                        }

                        IconButton(
                            onClick = {
                                scope.launch {
                                    val targetPage = (pagerState.currentPage + 1).coerceAtMost(animals.size - 1)
                                    pagerState.animateScrollToPage(targetPage)
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Go forward",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
