package charly.baquero.pocketmap

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform