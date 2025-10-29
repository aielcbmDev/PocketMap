package charly.baquero.pocketmap

import platform.Foundation.NSBundle

private const val IOS_MAPS_API_KEY = "IOS_MAPS_API_KEY"

class AppSecretsImpl : AppSecrets {

    override fun getMapsApiKey(): String {
        return NSBundle.mainBundle.objectForInfoDictionaryKey(IOS_MAPS_API_KEY) as? String ?: ""
    }
}
