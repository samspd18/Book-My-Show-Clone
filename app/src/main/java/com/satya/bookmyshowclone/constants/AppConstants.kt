package com.satya.bookmyshowclone.constants

class AppConstants {

    companion object {
        private const val twoFactorKey = "fe0800ab-2c2d-11ed-9c12-0200cd936042"
        const val twoFactorBaseUrl = "https://2factor.in/API/V1/${twoFactorKey}/SMS/"
        const val movieApiKey = "52a40c7199101f6f32e27fdd02c5f7dc"
        const val movieBaseUrl = "https://api.themoviedb.org/3/"
        const val movieImageBaseUrl = "https://image.tmdb.org/t/p/w500/"
        const val youtubeBaseUrl = "https://www.youtube.com/watch?v="
        const val RC_SIGN_IN = 1000
        const val sharedPrefFile = "BookMyShowSharedPreference"
    }
}