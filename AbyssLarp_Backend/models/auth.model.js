import pool from "../config/database.js";

export async function getUserByEmail(email) {

    const [rows] = await pool.query(
        `
        SELECT
        id,
        pseudo,               
        role
        FROM Utilisateur
        WHERE Email = ?
        `,
        [email]
    );    
    console.log("Résultat SQL :", rows);
    return rows[0];
}