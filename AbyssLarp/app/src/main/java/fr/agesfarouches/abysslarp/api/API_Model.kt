package fr.agesfarouches.abysslarp.api

import com.google.gson.annotations.SerializedName

data class GnListItem(
    @SerializedName("ID") val id: Int,
    @SerializedName("Nom") val nom: String,
    @SerializedName("Date_debut") val Date_debut: String,
    @SerializedName("Date_Fin") val Date_Fin: String,
    @SerializedName("Lieu") val lieu: String,
    @SerializedName("Image") val image: String?
)

data class GnDetail(
    @SerializedName("ID") val id: Int,
    @SerializedName("Nom") val nom: String,
    @SerializedName("Date_debut") val Date_debut: String,
    @SerializedName("Date_Fin") val Date_Fin: String,
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