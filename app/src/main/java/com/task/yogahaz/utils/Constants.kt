package com.task.yogahaz.utils



object UserData {

    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "
    var TOKEN = ""

}


object APIS {
    const val BASE_URL = "https://yogahez.mountasher.online/api/"
    const val LOGIN = "login"
    const val REGISTER = "client-register"
    const val CATEGORIES = "base-categories"
    const val TRENDING = "trending-sellers?filter=1"
    const val POPULAR = "popular-sellers?filter=1"
    const val ADD_TO_FAVORITE = "favorite"
}

object CONSTANTS {
    const val MIN_PASSWORD_LENGTH = 8
    const val MIN_NAME_LENGTH = 14
    const val CONNECT_TIMEOUT = 40L

}

