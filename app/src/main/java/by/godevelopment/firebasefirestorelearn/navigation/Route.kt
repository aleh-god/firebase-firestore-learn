package by.godevelopment.firebasefirestorelearn.navigation

import androidx.annotation.StringRes
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.domain.models.UiText

enum class Route(
    val label: String,
    @StringRes
    val screenName: Int
) {
    WELCOME("welcome", R.string.screen_name_welcome),
    SAVE_PERSON("save_person", R.string.screen_name_save_person),
    LOAD_PERSON("load_person", R.string.screen_name_load_person),
    LIST_PERSONS("list_persons", R.string.screen_name_list_persons)
}
