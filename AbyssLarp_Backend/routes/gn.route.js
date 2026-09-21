import express from 'express';
import * as gnController from '../controllers/gn.controller.js';
 
const router = express.Router();
 
router.get('/', gnController.getAll);
router.get('/:id', gnController.getById);
router.post('/', gnController.create);
router.put('/:id', gnController.update);
 
export default router;