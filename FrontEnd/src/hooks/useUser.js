import axios from "axios";
import API_BASE_URL from "../config/apiConfig";
import token from "../config/getToken";
import { useState } from "react";

function useUser() {

    const [message, setMessage] = useState(null)

    function excluirUsuario(user) {
        axios.delete(`${API_BASE_URL}/usuarios/${user.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`  
            }
        })
        .then(() => {
            localStorage.removeItem("token")
            window.location.reload()
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
        })
    }

    return {excluirUsuario, message}
}

export default useUser