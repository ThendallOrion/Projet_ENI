import * as gnService from '../services/gn.service.js';
 
export async function getAll(req, res) {
  try {
    const listeGN = await gnService.getGnList();
    res.json(listeGN);
  } catch (err) {
    console.error("ERREUR COMPLETE :", err);
    res.status(500).json({ error: err.message });
  }
}
 
export async function getById(req, res) {
  try {
    const gn = await gnService.getGnById(req.params.id);
    if (!gn) {
      return res.status(404).json({ error: 'GN introuvable' });
    }
    return res.status(200).json(gn);
  } catch (err) {
    console.error(err);
    return res.status(500).json({ error: 'Erreur serveur' });
  }
}
 
export async function create(req, res) {
  try {
    const nouveauGN = await gnService.createGN(req.body);
    res.status(201).json(nouveauGN);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: err.message });
  }
}
 
export async function update(req, res) {
  try {
    const modification = await gnService.updateGN(req.params.id, req.body);
    if (!modification) {
      return res.status(404).json({ error: "GN introuvable" });
    }
    res.status(200).json({ message: "GN modifié" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: err.message });
  }
}