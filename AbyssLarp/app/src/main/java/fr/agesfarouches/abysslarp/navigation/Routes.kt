package fr.agesfarouches.abysslarp.navigation

object Routes {
    const val HOME = "home"
    const val HOME_TEST = "home_test"
    const val GN_LIST = "gn_list"

    const val NFC_MENU = "nfc_menu"
    const val LOGIN_MENU = "login_menu"
    const val PROFIL = "profil"
    const val NEW_PAGE = "new_page"

    const val GN_DETAIL = "gn_detail/{id}"
    fun gnDetail(id: Int) = "gn_detail/$id"

    const val GAME_POWER = "game_power"
}

