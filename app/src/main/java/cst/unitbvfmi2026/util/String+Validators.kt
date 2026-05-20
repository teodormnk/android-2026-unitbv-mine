package cst.unitbvfmi2026.util

import android.util.Patterns

fun String.isValidEmail() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidPassword() = this.length > 5
fun String.isValidLocation() = this.length > 2
