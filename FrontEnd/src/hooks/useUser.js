import axios from "axios";
import API_BASE_URL from "../config/apiConfig";
import token from "../config/getToken";

function useUser() {

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
        .catch(err => console.log(err))
    }

    return {excluirUsuario}
}

export default useUser