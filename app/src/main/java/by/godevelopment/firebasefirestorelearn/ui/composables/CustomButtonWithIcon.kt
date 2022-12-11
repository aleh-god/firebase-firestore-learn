package by.godevelopment.firebasefirestorelearn.ui.composables

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.ui.theme.Values

@Composable
fun CustomButtonWithIcon(
    onClick: () -> Unit,
    buttonImage: ImageVector,
    @StringRes
    buttonText: Int,
    @StringRes
    buttonDescription: Int = R.string.cd_button_default,
    buttonPadding: PaddingValues = Values.customPadding
) {
    Button(
        onClick = onClick,
        contentPadding = buttonPadding
    ) {
        Icon(
            imageVector = buttonImage,
            contentDescription = stringResource(buttonDescription),
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(buttonText))
    }
}

@Preview(name = "Preview View", showBackground = true, backgroundColor = 0xFFC7BCA1)
@Composable
fun DefaultPreview() {
    CustomButtonWithIcon(
        onClick = {
            Log.i("Preview#", "DefaultPreview: onClick")
        },
        buttonImage = Icons.Filled.Person,
        buttonText = R.string.button_text_update
    )
}
