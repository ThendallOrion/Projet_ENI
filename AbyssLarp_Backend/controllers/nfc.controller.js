import * as nfcService from '../services/nfc.service.js';

export async function getByUid(req, res) {
  const uid = req.params.uid;

  try {
    const result = await nfcService.resolveTag(uid);

    if (result.unsupported) {
      return res.status(400).json({
        message: 'Type NFC non géré',
        type: result.type
      });
    }

    if (!result.found) {
      return res.status(404).json({ message: result.message });
    }

    return res.json(result.data);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Erreur serveur' });
  }
}

export async function associate(req, res) {
  const { uid, entityType, entityId } = req.body;

  if (!uid || !entityType || !entityId) {
    return res.status(400).json({ message: 'uid, entityType et entityId sont requis' });
  }

  try {
    await nfcService.associateTag(uid, entityType, entityId);
    res.status(200).json({ message: 'Association enregistrée' });
  } catch (error) {
    if (error.status === 400) {
      return res.status(400).json({ message: error.message });
    }
    console.error(error);
    res.status(500).json({ message: 'Erreur serveur' });
  }
}