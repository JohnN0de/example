package com.timeweb.checkdomain.presentation.extension

import android.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.timeweb.checkdomain.R
import kotlinx.android.synthetic.main.layout_toast_info.view.*

fun Fragment.hideKeyboard() {
    val activity = this.activity
    if (activity is AppCompatActivity) {
        activity.hideKeyboard()
    }
}

fun Fragment.getColor(@ColorRes color: Int) = ContextCompat.getColor(requireContext(), color)

fun Fragment.showInfoToast(message: String?, toastDuration: Int = Toast.LENGTH_LONG) {
    val toastView = View.inflate(requireContext(), R.layout.layout_toast_info, null)
    toastView.title.text = message
    Toast(requireContext()).apply {
        setGravity(Gravity.AXIS_X_SHIFT or Gravity.TOP or Gravity.FILL_HORIZONTAL, 32.dpToPx(), 0)
        duration = toastDuration
        view = toastView
        show()
    }

}

fun Fragment.showSimpleAlert(title: String?, message: String?) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .create().show()
}
