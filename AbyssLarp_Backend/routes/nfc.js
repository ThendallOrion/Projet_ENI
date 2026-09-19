const express = require("express");
const router = express.Router();

const db = require("../db/mysql");


router.get("/:uid", async (req, res) => {

    const uid = req.params.uid;


    try {

        // 1 - Cherche le NFC
        const [nfc] = await db.query(
            `
            SELECT entityType, entityId
            FROM GN_4_NfcTag
            WHERE UID = ?
            `,
            [uid]
        );


        if(nfc.length === 0){
            return res.status(404).json({
                message:"NFC inconnu"
            });
        }


        const entityType = nfc[0].entityType;
        const entityId = nfc[0].entityId;



        // 2 - Cherche selon le type

        switch(entityType){


            case "CHARACTER":

                const [character] = await db.query(
                    `
                    SELECT *
                    FROM GN_4_CHARACTER
                    WHERE ID = ?
                    `,
                    [entityId]
                );


                if(character.length === 0){
                    return res.status(404).json({
                        message:"Personnage introuvable"
                    });
                }


                return res.json({

                    type:"CHARACTER",
                    ...character[0]

                });



            case "STATION":

                const [station] = await db.query(
                    `
                    SELECT *
                    FROM GN_4_STATION
                    WHERE ID = ?
                    `,
                    [entityId]
                );


                return res.json({

                    type:"STATION",
                    ...station[0]

                });



            default:

                return res.status(400).json({

                    message:"Type NFC non géré",
                    type:entityType

                });

        }



    }
    catch(error){

        console.error(error);

        res.status(500).json({
            message:"Erreur serveur"
        });

    }


});


module.exports = router;