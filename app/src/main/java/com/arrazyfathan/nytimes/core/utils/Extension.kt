package com.arrazyfathan.nytimes.core.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}
