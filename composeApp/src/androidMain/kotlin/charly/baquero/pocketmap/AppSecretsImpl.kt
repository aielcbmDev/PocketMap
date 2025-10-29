package charly.baquero.pocketmap

class AppSecretsImpl : AppSecrets {

    override fun getMapsApiKey(): String {
        return BuildConfig.ANDROID_MAPS_API_KEY
    }
}
