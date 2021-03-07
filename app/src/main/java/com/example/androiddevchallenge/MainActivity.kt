/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

enum class CountdownState {
    START, STOP
}

// Start building your app here!
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        var countdownState by rememberSaveable { mutableStateOf(CountdownState.STOP) }
        var seconds by rememberSaveable { mutableStateOf(59) }
        var countDownTimer: CountDownTimer? by rememberSaveable { mutableStateOf(null) }
        val millisInASecond = TimeUnit.SECONDS.toMillis(1L)

        Timer(
            seconds = seconds,
            countdownState = countdownState,
            onClickUp = { seconds = (seconds + 1) % 60 },
            onClickDown = { seconds = if (seconds - 1 >= 0) seconds - 1 else 59 },
            onCountdownStateChange = {
                when (countdownState) {
                    CountdownState.START -> {
                        countdownState = CountdownState.STOP
                        countDownTimer?.cancel()
                        countDownTimer = null
                    }
                    CountdownState.STOP -> {
                        countdownState = CountdownState.START
                        countDownTimer = object : CountDownTimer(
                            seconds * millisInASecond,
                            millisInASecond
                        ) {
                            override fun onTick(millisUntilFinished: Long) {
                                seconds = (millisUntilFinished / millisInASecond).toInt() + 1
                            }

                            override fun onFinish() {
                                seconds = 0
                                countdownState = CountdownState.STOP
                            }
                        }
                        countDownTimer?.start()
                    }
                }
            })
    }
}

@ExperimentalAnimationApi
@Composable
fun Timer(
    seconds: Int,
    countdownState: CountdownState,
    onClickUp: () -> Unit,
    onClickDown: () -> Unit,
    onCountdownStateChange: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerItem(
            showButtons = countdownState == CountdownState.STOP,
            time = seconds,
            onClickUp = onClickUp,
            onClickDown = onClickDown
        )
        Spacer(modifier = Modifier.height(32.dp))
        Crossfade(targetState = countdownState) { state ->
            val drawable = when (state) {
                CountdownState.START -> R.drawable.ic_pause_24
                CountdownState.STOP -> R.drawable.ic_play_24
            }
            Button(onClick = onCountdownStateChange) {
                Image(
                    painter = painterResource(id = drawable),
                    contentDescription = null
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        Timer(
            seconds = 60,
            countdownState = CountdownState.STOP,
            onClickUp = { },
            onClickDown = { },
            onCountdownStateChange = { }
        )
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        Timer(
            seconds = 60,
            countdownState = CountdownState.STOP,
            onClickUp = { },
            onClickDown = { },
            onCountdownStateChange = { }
        )
    }
}
