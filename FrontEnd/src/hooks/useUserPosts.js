import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import { useEffect, useState } from "react"
import token from "../config/getToken"

function useUserPosts(userProfile) {

    const [postagensUsuario, setPostagensUsuario] = useState([])
    const [postsInteracoesUsuario, setPostsInteracoesUsuario] = useState([])
    const [postagenMidiaUsuario, setPostagensMidiaUsuario] = useState([])


    useEffect(() => {
        if (userProfile) {
            axios.get(`${API_BASE_URL}/postagem?id_user=${userProfile.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => setPostagensUsuario(response.data))
            .catch(err => console.log(err))

            axios.get(`${API_BASE_URL}/postagem?id_user=${userProfile.id}&allPosts=True`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => setPostsInteracoesUsuario(response.data))
            .catch(err => console.log(err))

            axios.get(`${API_BASE_URL}/postagem?id_user=${userProfile.id}&mediaPosts=True`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => setPostagensMidiaUsuario(response.data))
            .catch(err => console.log(err))
        }
    }, [userProfile])
    
    return {postagensUsuario, setPostagensUsuario, postsInteracoesUsuario, setPostsInteracoesUsuario, postagenMidiaUsuario, setPostagensMidiaUsuario}
}

export default useUserPosts