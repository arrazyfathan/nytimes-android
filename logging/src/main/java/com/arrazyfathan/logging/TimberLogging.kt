package com.arrazyfathan.logging

import timber.log.Timber

/**
 * Created by Ar Razy Fathan Rabbani on 19/05/23.
 */

/*
* Default Config
 */
class TimberLogging : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) on ${element.methodName}"
    }
}

/*
* With clickable line number
*/
class ClickableLineNumberDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        findLogCallStackTraceElement()?.let { element ->
            val lineNumberInfo = "(${element.fileName}: ${element.lineNumber})"
            val updatedMessage = "$lineNumberInfo: $message"
            super.log(priority, tag, updatedMessage, t)
        } ?: run {
            super.log(priority, tag, message, t)
        }
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        return element.className
    }

    private fun findLogCallStackTraceElement(): StackTraceElement? {
        val stackTrace = Throwable().stackTrace
        var foundDebugTree = false

        return stackTrace.firstOrNull() { element ->
            if (element.className.contains("ClickableLineNumberDebugTree")) {
                foundDebugTree = true
                false
            } else {
                foundDebugTree && !element.className.contains("Timber")
            }
        }
    }
}
