import axios from "axios";
import API_BASE_URL from "../config/apiConfig";
import token from "../config/getToken";

function useImage() {
    function getImage(user) {
        axios.get(`${API_BASE_URL}/img/${user.id}/${user.foto_perfil}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => {
            return response.data
        }).catch(err => console.log(err))
    }

    return {getImage}
}

export default useImage