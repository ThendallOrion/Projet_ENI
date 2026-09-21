import pool from '../config/database.js';

export async function findByUid(uid) {
    const [rows] = await pool.query(
        'SELECT entityType, entityId FROM GN_4_NfcTag WHERE UID = ?',
        [uid]
    );
    return rows[0] || null;
}

export async function getCharacter(id) {
    const [rows] = await pool.query(
        'SELECT * FROM GN_4_CHARACTER WHERE ID = ?',
        [id]
    );
    return rows[0] || null;
}

export async function getStation(id) {
    const [rows] = await pool.query(
        'SELECT * FROM GN_4_STATION WHERE ID = ?',
        [id]
    );
    return rows[0] || null;
}

export async function associate(uid, entityType, entityId) {
    await pool.query(
        `INSERT INTO GN_4_NfcTag (UID, entityType, entityId)
         VALUES (?, ?, ?)
         ON DUPLICATE KEY UPDATE entityType = ?, entityId = ?`,
        [uid, entityType, entityId, entityType, entityId]
    );
}