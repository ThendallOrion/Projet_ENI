import express from 'express';
import * as gnModel from '../models/gnModel.js';

const router = express.Router();

router.get('/', async (req, res) => {
    try {
        const listeGN = await gnModel.getGnList();
        res.json(listeGN);
    } catch (err) {
        console.error("ERREUR COMPLETE :", err);

        res.status(500).json({
            error: err.message
        });
    }
});

router.get('/:id', async (req, res) => {
  try {
    const gn = await gnModel.getGnById(req.params.id);
    if (!gn) {
      return res.status(404).json({ error: 'GN introuvable' });
    }
    return res.status(200).json(gn);
  } catch (err) {
    console.error(err);
    return res.status(500).json({ error: 'Erreur serveur' });
  }
});

router.post('/', async (req, res) => {
    try {
        const nouveauGN = await gnModel.createGN(req.body);
        res.status(201).json(nouveauGN);
    }
    catch(err) {
        console.error(err);
        res.status(500).json({
            error: err.message
        });
    }
});

router.put('/:id', async (req, res) => {
    try {
        const modification = await gnModel.updateGN(
            req.params.id,
            req.body
        );
        if (!modification) {
            return res.status(404).json({
                error: "GN introuvable"
            });
        }
        res.status(200).json({
            message: "GN modifié"
        });
    }
    catch(err) {
        console.error(err);
        res.status(500).json({
            error: err.message
        });
    }
});

export default router;