import * as gnModel from '../models/gn.model.js';

export async function getGnList() {
  return gnModel.getGnList();
}

export async function getGnById(id) {
  return gnModel.getGnById(id);
}

export async function createGN(gn) {
  return gnModel.createGN(gn);
}

export async function updateGN(id, gn) {
  return gnModel.updateGN(id, gn);
}