import express from 'express';
import * as gnController from '../controllers/gn.controller.js';
import { authenticateToken } from '../middleware/auth.middleware.js';

 
const router = express.Router();
 
router.get('/', gnController.getAll);
router.get('/:id', gnController.getById);
router.post('/', authenticateToken, gnController.create);
router.put('/:id', authenticateToken, gnController.update);
 
export default router;