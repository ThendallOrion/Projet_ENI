Project 

protection multi tentative
reset password
inscription
doit faire la protections des routes 

I.AbyssLARP_Backend

connexion DB de hostinger
création des routes
jwt et crytpé le mot de pass

ca sera le fournisseur de me founir certificat SSL pour etre en https

II.AbyssLARP
Kotlin

0.
connexion à l'api

1.
lit une liste de GN 
details d'un GN

2.
lit un NFC
recherche dans la base de donné et retour à quoi cela correspond

3.
login


III.list DB
CREATE TABLE `Liste_GN` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `Nom` VARCHAR(255) NOT NULL,
    `Date_debut` DATE NOT NULL,
    `Date_Fin` DATE NOT NULL,
    `Lieu` VARCHAR(255) NOT NULL,
    `Equipe_Orga` VARCHAR(255) NOT NULL,
    `Site_web` VARCHAR(255) NOT NULL,
    `Description` TEXT NOT NULL,
    `Ambiance` TEXT NOT NULL,
    `Liens_utiles` TEXT NOT NULL,
    `Places_PJ` INT NULL,
    `Prix_PJ` INT NOT NULL,
    `Prix_PNJ` INT NOT NULL,
    `Image` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`ID`)
) 

CREATE TABLE GN_4_NfcTag
(
    UID VARCHAR(20) NOT NULL,
    entityType ENUM(
        'CHARACTER',
        'OBJECT',
        'STATION
    ) NOT NULL,
    entityId INT NOT NULL,
    PRIMARY KEY (UID)
) 

CREATE TABLE GN_4_STATION 
(
    ID int NOT NULL,
    nom VARCHAR(100) NOT NULL   
    PRIMARY KEY (ID)
) 

CREATE TABLE CHARACTER 
(
    ID int NOT NULL,
    nom VARCHAR(100) NOT NULL, 
    age int 
    PRIMARY KEY (ID)
) 




