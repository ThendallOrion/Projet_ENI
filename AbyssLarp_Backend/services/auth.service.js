import bcrypt from "bcrypt";
import pool from "../config/database.js";
import jwt from "jsonwebtoken";
import * as authmodel from '../models/auth.model.js';

export async function registerUser(userData) {

    const {
        pseudo,
        email,
        password
    } = userData;

    // Vérifier si l'utilisateur existe déjà
    const [users] = await pool.query(
        `
        SELECT *
        FROM Utilisateur
        WHERE email = ? OR pseudo = ?
        `,
        [
            email,
            pseudo
        ]
    );

    //test Email ou pseudo déjà utilisé
    if (users.length > 0) {
        throw new Error(
            "Email ou pseudo déjà utilisé"
        );
    }

    // Création du hash
    const passwordHash = await bcrypt.hash(
        password,
        12 //valeur conseiller pour les application actuel
    );

    // Insertion dans la base
    // Protection du role faite par la DB donc on l'insert part
    // role =  VISITEUR
    const [result] = await pool.query(
        `
        INSERT INTO Utilisateur
        (
            pseudo,
            email,
            mot_de_passe_hash,            
        )
        VALUES (?, ?, ? )
        `,
        [
            pseudo,
            email,
            passwordHash,           
        ]
    );

    return {
        id: result.insertId,
        pseudo,
        email       
    };
}


export async function loginUser(data) {

    const { email, password } = data;

    // Recherche de l'utilisateur
    const userService = await authmodel.getUserByEmail(email);
    console.log("Email reçu :", email);
    console.log(userService);

    if (!userService) {
        throw new Error("Email ou mot de passe incorrect");
    }

    // Vérifie le mot de passe
    const passwordOk = await bcrypt.compare(
        password,
        userService.mot_de_passe_hash
    );

    if (!passwordOk) {
        throw new Error("Email ou mot de passe incorrect");
    }

    // Création du JWT
    const token = jwt.sign(
        {
            id: userService.id,
            email: userService.email,
            role: userService.role
        },
        process.env.JWT_SECRET,
        {
            expiresIn: process.env.JWT_EXPIRES_IN
        }
    );

    return {
        token,
        userService: {
            id: userService.id,
            pseudo: userService.pseudo,
            email: userService.email,
            role: userService.role
        }
    };
}