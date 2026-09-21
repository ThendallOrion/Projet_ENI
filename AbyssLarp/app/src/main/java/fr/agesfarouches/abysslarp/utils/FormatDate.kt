package fr.agesfarouches.abysslarp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(dateString: String): String {
    val instant = Instant.parse(dateString)

    val formatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy",
        Locale.FRANCE
    )

    return instant
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}