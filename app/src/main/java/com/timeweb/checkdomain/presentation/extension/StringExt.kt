package com.timeweb.checkdomain.presentation.extension

import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import java.util.*


fun String.formatPhoneWithMask(mask: String): String {
    val phoneMask = Mask(mask)
    return phoneMask.apply(
        CaretString(
            this,
            0,
            CaretString.CaretGravity.FORWARD(true)
        )
    ).formattedText.string
}

fun String.extractDigits(): String = filter { char -> char.isDigit() }

fun String.toLowerCaseWithLocale(locale: Locale = Locale("ru")) = this.toLowerCase(locale)

fun numWithPluralEnding(num: Int, pluralForms: Array<String>): String {
    val index =
        if (num % 10 == 1 && num % 100 != 11) 0 else if (num % 10 in 2..4 && (num % 100 < 10 || num % 100 >= 20)) 1 else 2
    return "$num ${pluralForms[index]}"
}

fun pluralEndingForNum(num: Int, pluralForms: Array<String>): String {
    val index =
        if (num % 10 == 1 && num % 100 != 11) 0 else if (num % 10 in 2..4 && (num % 100 < 10 || num % 100 >= 20)) 1 else 2
    return pluralForms[index]
}
