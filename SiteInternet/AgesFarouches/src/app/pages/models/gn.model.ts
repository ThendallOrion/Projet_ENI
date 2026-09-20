// Ajuste les champs pour qu'ils correspondent exactement à ce que renvoie
// l'API AbyssLarp pour un GN (regarde la réponse JSON réelle et compare).
export interface Gn {
  id: number;
  titre: string;
  dateDebut: string;
  dateFin: string;
  lieu: string;
  description: string;
}
