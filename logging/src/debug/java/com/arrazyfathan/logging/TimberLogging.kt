package com.arrazyfathan.logging

import timber.log.Timber

/**
 * Created by Ar Razy Fathan Rabbani on 19/05/23.
 */

class TimberLogging : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) on ${element.methodName}"
    }
}
