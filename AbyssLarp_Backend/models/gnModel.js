import pool from '../config/database.js';

export async function getGnList() {
  const [rows] = await pool.query(
    'SELECT ID, Nom, Date_debut,Date_Fin, Lieu, Image FROM Liste_GN ORDER BY Date_debut ASC'
  );
  return rows;
}

export async function getGnById(id) {
  const [rows] = await pool.query(
    'SELECT * FROM Liste_GN WHERE ID = ? LIMIT 1',
    [id]
  );
  return rows[0] || null;
}

export async function createGN(gn) {

    const [result] = await pool.query(
        `INSERT INTO Liste_GN
        (
            Nom,
            Date_debut,
            Date_Fin,
            Lieu,
            Equipe_Orga,
            Site_web,
            Description,
            Ambiance,
            Liens_utiles,
            Prix_PJ,
            Prix_PNJ,
            Image
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
        [
            gn.Nom,
            gn.Date_debut,
            gn.Date_Fin,
            gn.Lieu,
            gn.Equipe_Orga,
            gn.Site_web,
            gn.Description,
            gn.Ambiance,
            gn.Liens_utiles,
            gn.Prix_PJ,
            gn.Prix_PNJ,
            gn.Image
        ]
    ); 
    return {
        ID: result.insertId,
        ...gn
    };

}

export async function updateGN(id, gn) {

    const [result] = await pool.query(
        `UPDATE Liste_GN SET
            Nom = ?,
            Date_debut = ?,
            Date_Fin = ?,
            Lieu = ?,
            Equipe_Orga = ?,
            Site_web = ?,
            Description = ?,
            Ambiance = ?,
            Liens_utiles = ?,
            Prix_PJ = ?,
            Prix_PNJ = ?,
            Image = ?
        WHERE ID = ?`,
        [
            gn.Nom,
            gn.Date_debut,
            gn.Date_Fin,
            gn.Lieu,
            gn.Equipe_Orga,
            gn.Site_web,
            gn.Description,
            gn.Ambiance,
            gn.Liens_utiles,
            gn.Prix_PJ,
            gn.Prix_PNJ,
            gn.Image,
            id
        ]
    );


    return result.affectedRows > 0;

}