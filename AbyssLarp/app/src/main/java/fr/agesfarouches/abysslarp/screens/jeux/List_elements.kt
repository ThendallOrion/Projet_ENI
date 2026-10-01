package fr.agesfarouches.abysslarp.screens.jeux

// Zones du vaisseau : source unique pour les ids, libellés
enum class ZoneVaisseau(
    val label: String,
    val capaciteMax: Int = 0 // unités max que le nœud peut recevoir
) {
    GENERATEUR("Générateur"),
    FTL("Moteur FTL", capaciteMax = 4),
    SURVIE("Survie", capaciteMax = 3),
    LABO("Laboratoires", capaciteMax = 3),
    ARMEMENT("Armement", capaciteMax = 5),
    BATTERIE("Batterie", capaciteMax = 4)
}

// Nœuds empilés en colonne à droite (de haut en bas)
internal val ZonesEnergie = listOf(
    ZoneVaisseau.SURVIE,
    ZoneVaisseau.FTL,
    ZoneVaisseau.LABO,
    ZoneVaisseau.ARMEMENT,
    ZoneVaisseau.BATTERIE
)

const val PRODUCTION_GENERATEUR = 10