import axios from "axios"
import { useState } from "react"
import API_BASE_URL from "../config/apiConfig"
import useNotification from "./useNotification"


function useRelacao() {

    const [message, setMessage] = useState(null)

    const [relacao, setRelacao] = useState(null)
    const [numeroSeguidores, setNumeroSeguidores] = useState(0)
    const [numeroSeguidos, setNumeroSeguidos] = useState(0)
    const [seguidores, setSeguidores] = useState([])
    const [seguidos, setSeguidos] = useState([])
    const [unacceptedRelationships, setUnacceptedRelationships] = useState([])

    function seekRelationship(user, userProfile) {
        if (userProfile && user.id !== userProfile.id) {
            axios.get(`${API_BASE_URL}/relacao?id_seguidor=${user.id}&id_seguido=${userProfile.id}`)
            .then(response => {
                setRelacao(response.data)
            })
            .catch(err => {
                setMessage(err.response.data)
                setInterval(() => setMessage(null), 10000)
            })
        }
    }

    function getProfileUserRelationships(userProfile) {
        if (userProfile) {

            axios.get(`${API_BASE_URL}/relacao?id_seguidor=${userProfile.id}`)
            .then(response => {
                setSeguidos(response.data)
                setNumeroSeguidos(response.data.length)
            })
            .catch(err => {
                setMessage(err.response.data)
                setInterval(() => setMessage(null), 10000)
            })

            axios.get(`${API_BASE_URL}/relacao?id_seguido=${userProfile.id}`)
            .then(response => {
                setSeguidores(response.data)
                setNumeroSeguidores(response.data.length)
            })
            .catch(err => {
                setMessage(err.response.data)
                setInterval(() => setMessage(null), 10000)
            })
        }
    }

    function createRelationship(user, profileUser) {
        const relacao = {user, profileUser}
        const {createNotificationFollow} = useNotification()

        axios.post(`${API_BASE_URL}/relacao`, relacao)
        .then(response => {
            setRelacao(response.data)
            if (response.data.aceito) {
                createNotificationFollow(user, profileUser)
            }
        })
        .catch(err => {
            console.log(err)
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
        })
    }

    function removeRelationship(id) {
        axios.delete(`${API_BASE_URL}/relacao/${relacao.id}`)
        .then(() => {
            setRelacao(null)
            setNumeroSeguidores(prev => prev - 1)
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
        })
    }

    async function getCommomRelationships(user, profileUser) {
        if (profileUser && user.id !== profileUser.id) {
            try {
                const response = await axios.get(`${API_BASE_URL}/relacao/compartilhado?id_usuario=${user.id}&id_usuario_perfil=${profileUser.id}`)
                return response.data
            } catch (err) {
                setMessage(err.response.data)
                setInterval(() => setMessage(null), 10000)
            }
        }
    }

    function getUnacceptedRelationships(user) {
        axios.get(`${API_BASE_URL}/relacao?id_seguido=${user.id}&pending=True`)
        .then(response => setUnacceptedRelationships(response.data))
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            })
        })
    }

    function acceptRelationship(id, user, follower) {

        const {createNotificationFollow} = useNotification()
        
        axios.patch(`${API_BASE_URL}/relacao/${id}`)
        .then(() => {
            setUnacceptedRelationships(unacceptedRelationships.filter(relationship => relationship.id !== id))
            createNotificationFollow(follower, user)
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            })
        })
    }
    
    return {relacao, numeroSeguidores, numeroSeguidos, seguidores, seguidos, unacceptedRelationships, seekRelationship, getProfileUserRelationships, getCommomRelationships, createRelationship, removeRelationship, acceptRelationship, getUnacceptedRelationships}
}

export default useRelacao