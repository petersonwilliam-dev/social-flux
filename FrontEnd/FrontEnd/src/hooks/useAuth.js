import axios from "axios";
import { useState } from "react";
import API_BASE_URL from "../config/apiConfig";
import { useDispatch } from "react-redux";
import { alterarUsuario } from "../redux/reducer/userReducer";

function useAuth() {
    const dispatch = useDispatch()
    const [message, setMessage] = useState(null)

    function login({email, senha}) {
        axios.post(`${API_BASE_URL}/login`, {email, senha})
        .then(response => dispatch(alterarUsuario(response.data)))
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    function register(usuario) {
        axios.post(`${API_BASE_URL}/register`, usuario)
        .then(response => dispatch(alterarUsuario(response.data)))
        .catch(err => {
            setError(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    return {login, register, message, setMessage}
}

export default useAuth