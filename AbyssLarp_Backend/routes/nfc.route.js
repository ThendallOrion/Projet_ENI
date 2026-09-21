import express from 'express';
import * as nfcController from '../controllers/nfc.controller.js';

const router = express.Router();

router.get('/:uid', nfcController.getByUid);
router.post('/associate', nfcController.associate);

export default router;