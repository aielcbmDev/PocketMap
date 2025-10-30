package com.charly.networking.utils

import android.content.Context
import android.content.pm.ApplicationInfo

class BuildConfigImpl(
    private val applicationContext: Context
) : BuildConfig {

    override fun isDebug(): Boolean {
        return 0 != applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }
}
