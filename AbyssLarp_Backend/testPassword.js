import bcrypt from "bcrypt";


const motDePasse = "MonMotDePasse123";


async function test(){

    const hash = await bcrypt.hash(motDePasse,10);

    console.log("Mot de passe :", motDePasse);
    console.log("Hash :", hash);

}


test();
test();
test();



const motDePasseEntre = "MonMotDePasse123";
const hashStocke = "$2b$10$9U2wxYtqyomW0GT2SeGyLeoczIdyxqFY1wLCUkwSF02DJ9C8NAzyK";

async function test2(){

    const resultat = await bcrypt.compare(
        motDePasseEntre,
        hashStocke
    );

    console.log("Mot de passe valide ?", resultat);

}


test2();