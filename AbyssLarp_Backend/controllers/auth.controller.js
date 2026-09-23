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
        
        /*res.json({
            message:"Connexion réussie",
            user
        });*/
        
        res.json({            
            id: userController.userService.id,
            pseudo: userController.userService.pseudo,
            accessToken: userController.token
        });

        //renvoie que les données utilent
        /*res.json({
            id: user.id,
            pseudo: user.pseudo,
            accessToken: token
        });*/

    } catch(error) {
        console.error(error);
        res.status(401).json({
            error:error.message
        });
    }
}
