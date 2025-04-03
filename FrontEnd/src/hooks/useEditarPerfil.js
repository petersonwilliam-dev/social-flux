import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import token from "../config/getToken"

function useEditarPerfil(user) {

    const [message, setMessage] = useState(null)
    const navigate = useNavigate()

    function editarPerfil(values) {
        axios.patch(`${API_BASE_URL}/usuarios/${user.id}`, values, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => {
            localStorage.setItem("token", response.data)
            navigate(`/perfil/${user.username}`, {replace: true})
        })
        .catch(err => setMessage(err.response.data))
    }

    function atualizarFotoPerfil(values) {
        const formData = new FormData()
        formData.append("foto_perfil", values.foto_perfil)
        formData.append("foto_antiga", user.foto_perfil)
        

        axios.patch(`${API_BASE_URL}/img/usuarios/${user.id}`, formData, {
            headers: {
                "Content-Type" : "multipart/form-data",
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => {
            localStorage.setItem("token", response.data)
            navigate(`/perfil/${user.username}`, {replace: true})
        })
        .catch(err => setMessage(err.response.data))
    }

    return {editarPerfil, atualizarFotoPerfil, message, setMessage}
} 

export default useEditarPerfil