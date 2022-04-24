package com.example.core.utils

import android.util.Log

class ALogger {

    companion object {
        const val APP_MOVIEAPP = "MOVIE_CLEAN"

        const val CHANNEL_DEBUG = 1
        const val CHANNEL_INFO = 1
        const val CHANNEL_VERBOSE = 1

        const val TAG_DEBUG = "DEBUG"
        const val TAG_INFO = "INFO"
        const val TAG_ERROR = "ERROR"


        var logChannel: Int = CHANNEL_DEBUG

        private fun privateLog(tag: String, any: String) {
            when (logChannel) {
                CHANNEL_DEBUG -> {
                    Log.d(tag, any)
                }
                CHANNEL_INFO -> {
                    Log.i(tag, any)
                }
                CHANNEL_VERBOSE -> {
                    Log.v(tag, any)
                }
            }
        }

        fun log(tag: String, any: String) =
            privateLog(tag, any)

        fun dLog(any: String) =
            privateLog("${APP_MOVIEAPP}_$TAG_DEBUG", any)

        fun iLog(any: String) =
            privateLog("${APP_MOVIEAPP}_$TAG_INFO", any)

        fun eLog(any: String) =
            privateLog("${APP_MOVIEAPP}_$TAG_ERROR", any)
    }
}