import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import useNotification from "./useNotification"
import { useState } from "react"
import token from "../config/getToken"

function useCurtida() {

    const [message, setMessage] = useState(null)

    async function criarCurtida(user, postagem) {

        const {createNotificationLike} = useNotification()
        const curtida = {
            id_usuario: user.id,
            id_conteudo: postagem.id
        }

        try {
            const response = await axios.post(`${API_BASE_URL}/curtida`, curtida, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            createNotificationLike(user, postagem)
            return response.data
        } catch (err) {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
            return null
        }

    }

    async function buscarCurtida(user, postagem) {
        try {
            const response = await axios.get(`${API_BASE_URL}/curtida?id_usuario=${user.id}&id_conteudo=${postagem.id}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            return response.data
        } catch (err) {
            console.log(err)
            return null
        }
    }

    function removerCurtida(curtida, setCurtida, setNumeroCurtida) {
        axios.delete(`${API_BASE_URL}/curtida/${curtida.id}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        .then(() => {
            setCurtida(null)
            setNumeroCurtida(prev => prev - 1)
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
        })
    }

    async function buscarNumeroCurtidas(postagem) {
        try {
            const response = await axios.get(`${API_BASE_URL}/curtida/numero/${postagem.id}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            return response.data
        } catch (err) {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
            return 0
        }
    }

    return {criarCurtida, buscarCurtida, removerCurtida, buscarNumeroCurtidas, message, setMessage}
}

export default useCurtida