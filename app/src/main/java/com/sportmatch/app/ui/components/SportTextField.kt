package com.sportmatch.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.CornerMedium
import com.sportmatch.app.ui.theme.DividerLight
import com.sportmatch.app.ui.theme.TextSecondary
import com.sportmatch.app.ui.theme.SportMatchTheme

/**
 * SportMatch Custom Text Field
 * Styled input field with placeholder and focus states.
 *
 * @param value Current input value
 * @param onValueChange Callback when text changes
 * @param placeholder Placeholder text displayed when empty
 * @param modifier Modifier for sizing and positioning
 * @param enabled Whether the field is editable
 * @param singleLine Whether to constrain to single line
 * @param textStyle Custom text style
 */
@Composable
fun SportTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.textfield_height)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CornerMedium
            )
            .border(
                width = 1.dp,
                color = DividerLight,
                shape = CornerMedium
            )
            .padding(Dimens.medium),
        enabled = enabled,
        singleLine = singleLine,
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = textStyle,
                        color = TextSecondary
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SportTextFieldPreview() {
    SportMatchTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.large)
        ) {
            SportTextField(
                value = "",
                onValueChange = { },
                placeholder = "Enter your email"
            )
        }
    }
}

