package fr.agesfarouches.abysslarp.api

import com.google.gson.annotations.SerializedName

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val id: Int,
    val pseudo: String,
    val accessToken: String
)
data class GnListItem(
    @SerializedName("ID") val id: Int,
    @SerializedName("Nom") val nom: String,
    @SerializedName("Date_debut") val dateDebut: String,
    @SerializedName("Date_Fin") val dateFin: String,
    @SerializedName("Lieu") val lieu: String,
    @SerializedName("Equipe_Orga") val equipeOrga: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Image") val image: String?
)

data class GnDetail(
    @SerializedName("ID") val id: Int,
    @SerializedName("Nom") val nom: String,
    @SerializedName("Date_debut") val dateDebut: String,
    @SerializedName("Date_Fin") val dateFin: String,
    @SerializedName("Lieu") val lieu: String,
    @SerializedName("Equipe_Orga") val equipeOrga: String,
    @SerializedName("Site_web") val siteWeb: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Ambiance") val ambiance: String,
    @SerializedName("Liens_utiles") val liensUtiles: String,
    @SerializedName("Prix_PJ") val prixPJ: Int,
    @SerializedName("Prix_PNJ") val prixPNJ: Int,
    @SerializedName("Image") val image: String?
)

data class NfcLookupResponse(
    @SerializedName("type") val type: String?,
    @SerializedName("ID") val id: Int? = null,
    @SerializedName("Nom") val nom: String? = null,
    @SerializedName("Age") val age: Int? = null,
    @SerializedName("message") val message: String? = null
)

data class NfcAssociateRequest(
    @SerializedName("uid") val uid: String,
    @SerializedName("entityType") val entityType: String,
    @SerializedName("entityId") val entityId: Int
)