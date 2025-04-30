package com.portafolio.vientos_del_sur

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform