const express = require('express');
const router = express.Router();

const gnModel = require('../models/gnModel');


router.get('/', async function(req,res){

    try {

        const listeGN = await gnModel.getAllGN();

        res.json(listeGN);

    }
    catch(err){

    console.error("ERREUR COMPLETE :", err);

    res.status(500).json({
        error: err
    });

    }

});


module.exports = router;