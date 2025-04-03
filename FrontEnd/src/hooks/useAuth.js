import axios from "axios";
import { useState } from "react";

function useAuth() {

    const [message, setMessage] = useState(null)

    function login({email, senha}) {
        axios.post(`http://localhost:8000/login`, {email, senha})
        .then(response => {
            localStorage.setItem("token", response.data)
            window.location.reload()
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    function register(usuario) {
        axios.post(`http://localhost:8000/register`, usuario)
        .then(response => {
            localStorage.setItem("token", response.data)
            window.location.reload()
        })
        .catch(err => {
            setError(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    function logout() {
        localStorage.removeItem("token")
        window.location.reload()
    }

    return {login, register, message, setMessage, logout}
}

export default useAuth