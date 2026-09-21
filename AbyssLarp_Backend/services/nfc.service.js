import * as nfcModel from '../models/nfc.model.js';
import  '../config/variable_update.js';

export async function resolveTag(uid) {
  const tag = await nfcModel.findByUid(uid);

  if (!tag) {
    return { found: false, message: 'NFC inconnu' };
  }

  if (tag.entityType === 'CHARACTER') {
    const character = await nfcModel.getCharacter(tag.entityId);
    if (!character) {
      return { found: false, message: 'Personnage introuvable' };
    }
    return { found: true, data: { type: 'CHARACTER', ...character } };
  }

  if (tag.entityType === 'STATION') {
    const station = await nfcModel.getStation(tag.entityId);
    if (!station) {
      return { found: false, message: 'Station introuvable' };
    }
    return { found: true, data: { type: 'STATION', ...station } };
  }

  return { found: false, unsupported: true, type: tag.entityType };
}

export async function associateTag(uid, entityType, entityId) {
  const validTypes = NFC_validTypes;
  if (!validTypes.includes(entityType)) {
    const error = new Error('entityType invalide');
    error.status = 400;
    throw error;
  }
  return nfcModel.associate(uid, entityType, entityId);
}