package com.timeweb.checkdomain.presentation.extension

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

fun EditText.getContent() = this.text.toString().trim()

fun EditText.passwordVisibilityToggle() {
    transformationMethod = if (transformationMethod is PasswordTransformationMethod) {
        HideReturnsTransformationMethod.getInstance()
    } else {
        PasswordTransformationMethod.getInstance()
    }
    setSelection(text.toString().length)
}

fun TextInputLayout.clearErrorWhenStartTyping() {
    this.editText?.let {
        it.doAfterTextChanged {
            this.error = ""
        }
    }
}

fun EditText.addSuffix(
    suffix: String,
    filterPattern: String? = null,
    onTextChanged: ((extractedValue: String, formattedValue: String) -> Unit)? = null
) {
    val editText = this
    val formattedSuffix = " $suffix"
    var text = ""
    var isSuffixModified = false
    val pattern: Pattern? = filterPattern?.let { Pattern.compile(it) }

    val setCursorPosition: () -> Unit =
        {
            if (editableText.isEmpty())
                Selection.setSelection(editableText, 0)
            else {
                Selection.setSelection(
                    editableText,
                    editableText.length - formattedSuffix.length
                )
            }
        }

    val setEditText: () -> Unit = {
        editText.setText(text)
        setCursorPosition()
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val newText = editable.toString()

            if (newText == formattedSuffix) {
                //Если поле ввода пустое, то удаляем suffix
                this@addSuffix.removeTextChangedListener(this)
                text = ""
                setEditText()
                this@addSuffix.addTextChangedListener(this)
                onTextChanged?.invoke(text.replace(formattedSuffix, ""), "")
                return
            }

            if (isSuffixModified) {
                //пользователь пытается изменить sufix
                isSuffixModified = false
                setEditText()
            } else if (text.isNotEmpty() && newText.length < text.length && !newText.contains(
                    formattedSuffix
                )
            ) {
                //пользователь пытается удалить suffix
                setEditText()
            } else if (!newText.contains(formattedSuffix)) {
                //новая строка, нужно добавить suffix

                val matcher: Matcher? = pattern?.matcher(newText.replace(formattedSuffix, ""))

                if (matcher?.matches() == true)
                    text = "$newText$formattedSuffix"


                setEditText()
            } else {
                val matcher: Matcher? = pattern?.matcher(newText.replace(formattedSuffix, ""))

                if (matcher?.matches() == true)
                    text = newText
                else
                    setEditText()
            }
            onTextChanged?.invoke(text.replace(formattedSuffix, ""), text)
        }


        override fun beforeTextChanged(
            charSequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            charSequence?.let {
                val textLengthWithoutSuffix = it.length - formattedSuffix.length
                if (it.isNotEmpty() && start > textLengthWithoutSuffix) {
                    isSuffixModified = true
                }
            }
        }

        override fun onTextChanged(
            charSequence: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
        }
    })
}