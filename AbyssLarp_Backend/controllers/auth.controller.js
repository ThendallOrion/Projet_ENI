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
        const userController = await authservice.loginUser(req.body);
        //test si la Connexion réussie et renvoie toutes les donnée
        
        //retourne le jwt avec juste les donnée
        res.json({           
           
            accessToken: userController.token
        });       

    } catch(error) {
        console.error(error);
        res.status(401).json({
            error:error.message
        });
    }
}
