package com.sm.jetpackcountrycode.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sm.jetpackcountrycode.ui.theme.BBlue
import com.sm.jetpackcountrycode.ui.theme.BSky
import com.sm.jetpackcountrycode.ui.theme.poppin_font

@Composable
fun CountrySearchTextField(
    searchText: TextFieldState,
){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) BBlue else BSky.copy(0.7f),
        animationSpec = tween(durationMillis = 300),
        label = "borderColor"
    )





    BasicTextField(
        state = searchText,
        lineLimits = TextFieldLineLimits.Default,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp) // Border radius for background
            )
            .border(
                width = 2.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(12.dp)
            ).padding(horizontal = 18.dp),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        onKeyboardAction = KeyboardActionHandler{
            focusManager.clearFocus()
        },
        textStyle = TextStyle(
            fontSize = 24.sp,
            color = Color.Black,
            fontFamily = poppin_font,
            fontWeight = FontWeight.Medium
        ),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = 6.dp,
                            end = 6.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                ) {
                    if (searchText.text.isEmpty()) {
                        Text(
                            text = "Country name or code",
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color.Gray,
                                fontFamily = poppin_font,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    innerTextField()
                }
            }
        },
    )
}