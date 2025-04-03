import { jwtDecode } from "jwt-decode";

function decodeJwt(jwt) {
    if (typeof jwt !== "string" || !jwt.trim()) {
        console.log("Token inválido: não é uma string válida.");
        return null;
    }

    try {
        const jwtDecoded = jwtDecode(jwt)

        if (!jwtDecoded) {
            console.log("Erro ao decodificar: estrutura do token inválida.");
            return null;
        }

        if (jwtDecoded.exp && jwtDecoded.exp * 1000 < Date.now()) {
            return null;
        }

        try {
            
            return JSON.parse(jwtDecoded.sub);
        } catch (parseError) {
            console.log("Erro ao converter 'sub' para JSON:", parseError);
            return null;
        }

    } catch (err) {
        if (err instanceof SyntaxError) {
            console.log("Erro de sintaxe ao decodificar o token:", err);
        } else {
            console.log("Erro desconhecido ao processar o token:", err);
        }
        return null;
    }
}

export default decodeJwt;