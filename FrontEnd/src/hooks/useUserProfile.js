import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import { useState, useEffect } from "react"
import token from "../config/getToken"

function useUserProfile(username) {

    const [userProfile, setUserProfile] = useState(null)
    const [message , setMessage] = useState(null)

    useEffect(() => {
        axios.get(`${API_BASE_URL}/usuarios?username=${username}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setUserProfile(response.data))
        .catch(err => {
            setMessage(err.response)
            setInterval(() => setMessage(null), 10000)
        })
    }, [username])

    return {userProfile, setUserProfile, message, setMessage}
}

export default useUserProfile