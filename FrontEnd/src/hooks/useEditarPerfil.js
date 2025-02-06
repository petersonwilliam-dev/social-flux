import axios from "axios"
import API_BASE_URL from "../config/apiConfig"

import { useState } from "react"
import { useDispatch } from "react-redux"
import { alterarUsuario } from "../redux/reducer/userReducer"
import { useNavigate } from "react-router-dom"

function useEditarPerfil(user) {

    const [message, setMessage] = useState(null)
    const dispatch = useDispatch()
    const navigate = useNavigate()

    function editarPerfil(values) {
        axios.patch(`${API_BASE_URL}/usuarios/${user.id}`, values)
        .then(response => {
            dispatch(alterarUsuario(response.data))
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
                "Content-Type" : "multipart/form-data"
            }
        })
        .then(response => {
            dispatch(alterarUsuario(response.data))
            navigate(`/perfil/${user.username}`, {replace: true})
        })
        .catch(err => setMessage(err.response.data))
    }

    return {editarPerfil, atualizarFotoPerfil, message, setMessage}
} 

export default useEditarPerfil