package com.appiness.assignmentappiness.utils

import android.util.Log
import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE


class AndroidLogger(level: Level = Level.INFO) : Logger(level) {

    override fun log(level: Level, msg: MESSAGE) {
        if (this.level <= level) {
            logOnLevel(msg, level)
        }
    }

    private fun logOnLevel(msg: MESSAGE, level: Level) {
        when (level) {
            Level.DEBUG -> Log.d(KOIN_TAG, msg)
            Level.INFO -> Log.i(KOIN_TAG, msg)
            Level.ERROR -> Log.e(KOIN_TAG, msg)
        }
    }
}