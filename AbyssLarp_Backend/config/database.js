require('dotenv').config();

const mysql = require('mysql2/promise');

const pool = mysql.createPool({

    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,

    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0

});


console.log("Pool MySQL créé");


module.exports = pool;

(async()=>{

    try {

        const [result] = await pool.query("SELECT 1");

        console.log("Test MySQL OK", result);

    }
    catch(err){

        console.error("Test MySQL KO", err);

    }

})();