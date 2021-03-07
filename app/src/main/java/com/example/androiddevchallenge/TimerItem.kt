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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

@ExperimentalAnimationApi
@Composable
fun TimerItem(showButtons: Boolean, time: Int, onClickUp: () -> Unit, onClickDown: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(showButtons) {
            Button(onClick = onClickUp) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_up_24),
                    contentDescription = null
                )
            }
        }
        Text(text = time.toString(), style = typography.h1)
        AnimatedVisibility(showButtons) {
            Button(onClick = onClickDown) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_down_24),
                    contentDescription = null
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun TimerItemWithButtonsPreview() {
    MyTheme {
        TimerItem(showButtons = true, time = 60, onClickUp = {}, onClickDown = {})
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun TimerItemWithoutButtonsPreview() {
    MyTheme {
        TimerItem(showButtons = false, time = 60, onClickUp = {}, onClickDown = {})
    }
}
