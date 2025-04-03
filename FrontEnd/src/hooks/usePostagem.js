import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import { useNavigate } from "react-router-dom"
import useNotification from "./useNotification"
import { useState } from "react"
import token from "../config/getToken"

function usePostagem() {

    const navigate = useNavigate()
    const [error, setError] = useState(null)
    const [message, setMessage] = useState(null)
    const [homePosts, setHomePosts] = useState([])

    function criarPostagem(values, postagem) {

        const {createNotificationComent} = useNotification(token)

        const formData = new FormData()

        formData.append("legenda", values.legenda)
        formData.append("imagem", values.imagem)
        if (postagem) formData.append("id_postagem", postagem.id)

        axios.post(`${API_BASE_URL}/postagem`, formData, {
            headers: {
                'Content-Type' : 'multipart/form-data',
                "Authorization": `Bearer ${token}`
            }
        })
        .then(() => {
            if (postagem) createNotificationComent(user, postagem, values.legenda)
            navigate(0)
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    function removerPostagem(id) {
        axios.delete(`${API_BASE_URL}/postagem/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(() => {
            navigate(".", {replace: true})
        })
        .catch(err => {
            setMessage(err.response.data)
            setMessage(() => {
                setError(null)
            }, 10000)
        })
    }

    async function buscarPostagem(id) {
        try {
            const response = await axios.get(`${API_BASE_URL}/postagem/${id}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            return response.data
        } catch (err) {
            setMessage(err.response.data)
            setMessage(() => {
                setError(null)
            }, 10000)
            return null
        }
    }
    
    async function buscarNumeroRespostas(id) {

        try {
            const response = await axios.get(`${API_BASE_URL}/postagem?id_postagem=${id}&responses=True`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            return response.data.length
        } catch (err) {
            setMessage(err.response.data)
            setMessage(() => {
                setError(null)
            }, 10000)
            return 0
        }
    }

    async function buscarRespostas(id) {

        try {
            const response = await axios.get(`${API_BASE_URL}/postagem?id_postagem=${id}&responses=True`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            return response.data
        } catch (err) {
            setMessage(err.response.data)
            setMessage(() => {
                setError(null)
            }, 10000)
            return []
        }
    }


    function getHomePosts(user) {
        axios.get(`${API_BASE_URL}/postagem?id_seguidor=${user.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setHomePosts(response.data))
        .catch(err => {
            setMessage(err.reponse.data)
            setInterval(() => setMessage(null), 10000)
        })
    }

    return {criarPostagem, removerPostagem, buscarPostagem, buscarRespostas, buscarNumeroRespostas, buscarRespostas, getHomePosts, setMessage, setHomePosts,message, homePosts}
}

export default usePostagem