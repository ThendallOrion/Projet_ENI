import * as authservice from '../services/auth.service.js';

export async function register(req, res) {
    try {
        const user = await authservice.registerUser(req.body);
        res.status(201).json({
            message: "Utilisateur créé",
            user
        });

    } catch(error) {
        res.status(400).json({
            error:error.message
        });
    }
}

export async function login(req, res) {
    try {        
        const user = await authservice.loginUser(req.body);

        res.json({
            message:"Connexion réussie",
            user
        });

    } catch(error) {
        console.error(error);
        res.status(401).json({
            error:error.message
        });
    }
}
