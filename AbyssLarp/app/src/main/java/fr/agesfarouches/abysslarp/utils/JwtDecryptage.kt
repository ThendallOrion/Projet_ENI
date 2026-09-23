package fr.agesfarouches.abysslarp.utils

import android.util.Base64
import com.google.gson.Gson

data class JwtPayload(
    val id: Int,
    val pseudo: String,
    val email: String,
    val role: String,
    val iat: Long? = null,
    val exp: Long? = null
)

object JwtUtils {

    fun decode(token: String): JwtPayload? {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null

            val payloadJson = String(
                Base64.decode(
                    parts[1],
                    Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
                )
            )
            Gson().fromJson(payloadJson, JwtPayload::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun isExpired(token: String): Boolean {
        val exp = decode(token)?.exp ?: return true
        return System.currentTimeMillis() / 1000 >= exp
    }
}