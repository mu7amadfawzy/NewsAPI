package com.tempo.news.utils

object ValidationUtils {
    // A placeholder contact name validation check
    fun isFullNameValid(string: String?): Boolean {
        return (string!=null&&string.isNotBlank())
    }

    // A placeholder phone validation check
    fun isPhoneValid(phone: String?): Boolean {
        return phone!=null&&phone.isNotBlank() &&phone.matches("^[+]?[0-9]{10,13}\$".toRegex())
    }
}