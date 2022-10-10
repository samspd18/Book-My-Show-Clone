package com.satya.bookmyshowclone.model.city

data class City(var name: String?,var imageUrl: String? )


class CityArray  {

    companion object {
        val categories = arrayOf(
            City("Delhi-NCR ", "https://cdn-icons-png.flaticon.com/512/444/444767.png"),
            City("Bhubaneswar","https://cdn-icons-png.flaticon.com/512/444/444844.png"),
            City("Bengaluru","https://cdn-icons-png.flaticon.com/128/48/48780.png"),
            City("Mumbai","https://cdn-icons-png.flaticon.com/512/1058/1058658.png"),
            City("Lucknow","https://cdn-icons-png.flaticon.com/512/6564/6564525.png"),
            City("Hyderabad","https://img.icons8.com/external-sbts2018-outline-sbts2018/2x/external-hyderabad-charminar-monuments-sbts2018-outline-sbts2018.png"),
            City("Punjab","https://cdn-icons-png.flaticon.com/128/1283/1283984.png"),
            City("Ahmedabad","https://cdn-icons-png.flaticon.com/512/4879/4879583.png"),
            City("Jaipur ","https://cdn-icons-png.flaticon.com/512/5401/5401476.png"),
            City("Kolkata","https://cdn-icons-png.flaticon.com/512/4320/4320204.png"),
            City("","https://coolbackgrounds.io/images/backgrounds/white/pure-white-background-85a2a7fd.jpg"),
            City("","https://coolbackgrounds.io/images/backgrounds/white/pure-white-background-85a2a7fd.jpg"),
            )
    }

}