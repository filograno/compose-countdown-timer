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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
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
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        var countdownState by rememberSaveable { mutableStateOf(CountdownState.STOP) }
        var seconds by rememberSaveable { mutableStateOf(60) }
        var countDownTimer: CountDownTimer? by rememberSaveable { mutableStateOf(null) }
        val millisInASecond = TimeUnit.SECONDS.toMillis(1L)

        Timer(seconds = seconds, countdownState = countdownState) {
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
        }
    }
}

@Composable
fun Timer(seconds: Int, countdownState: CountdownState, onCountdownStateChange: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = seconds.toString(), style = typography.h1)
        Spacer(modifier = Modifier.height(16.dp))
        val buttonText = when (countdownState) {
            CountdownState.START -> "Stop"
            CountdownState.STOP -> "Start"
        }
        Button(onClick = onCountdownStateChange) {
            Text(text = buttonText, style = typography.h3)
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        Timer(
            seconds = 60,
            countdownState = CountdownState.STOP,
            onCountdownStateChange = { })
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        Timer(
            seconds = 60,
            countdownState = CountdownState.STOP,
            onCountdownStateChange = { })
    }
}
