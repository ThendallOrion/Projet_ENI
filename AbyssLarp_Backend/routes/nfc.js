import express from 'express';
import * as nfcModel from '../models/nfcModel.js';

const router = express.Router();

router.get('/:uid', async (req, res) => {
    const uid = req.params.uid;

    try {
        const tag = await nfcModel.findByUid(uid);

        if (!tag) {
            return res.status(404).json({ message: 'NFC inconnu' });
        }

        switch (tag.entityType) {
            case 'CHARACTER': {
                const character = await nfcModel.getCharacter(tag.entityId);
                if (!character) {
                    return res.status(404).json({ message: 'Personnage introuvable' });
                }
                return res.json({ type: 'CHARACTER', ...character });
            }

            case 'STATION': {
                const station = await nfcModel.getStation(tag.entityId);
                if (!station) {
                    return res.status(404).json({ message: 'Station introuvable' });
                }
                return res.json({ type: 'STATION', ...station });
            }

            default:
                return res.status(400).json({
                    message: 'Type NFC non géré',
                    type: tag.entityType
                });
        }
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Erreur serveur' });
    }
});

router.post('/associate', async (req, res) => {
    const { uid, entityType, entityId } = req.body;

    if (!uid || !entityType || !entityId) {
        return res.status(400).json({ message: 'uid, entityType et entityId sont requis' });
    }
    if (!['CHARACTER', 'OBJECT', 'STATION'].includes(entityType)) {
        return res.status(400).json({ message: 'entityType invalide' });
    }

    try {
        await nfcModel.associate(uid, entityType, entityId);
        res.status(200).json({ message: 'Association enregistrée' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Erreur serveur' });
    }
});

export default router;