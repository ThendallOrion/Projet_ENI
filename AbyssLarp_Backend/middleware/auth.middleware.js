import jwt from "jsonwebtoken";

export function authenticateToken(req, res, next) {

    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];

    if (!token) {
        return res.status(401).json({
            error:"Token manquant"
        });
    }

    jwt.verify(
        token,
        process.env.JWT_SECRET,
        (error, user)=>{
            if(error){
                return res.status(403).json({
                    error:"Token invalide"
                });
            }
            req.user = user;
            next();
        }
    );
}