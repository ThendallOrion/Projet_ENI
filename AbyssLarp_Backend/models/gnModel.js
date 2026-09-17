const db = require('../config/database');


async function getAllGN(){

    const [rows] = await db.query(
        "SELECT * FROM Liste_GN"
    );

    return rows;

}


module.exports = {
    getAllGN
};