import axios from "axios"
import API_BASE_URL from "../config/apiConfig"
import { useEffect, useState } from "react"
import token from "../config/getToken"

function useSearchUsuario() {

    const [search, setSearch] = useState('')
    const [usuariosPesquisados, setUsuariosPesquisados] = useState([])

    useEffect(() => {
        if (search != "" && search.trim().length > 0) {
            axios.get(`${API_BASE_URL}/usuarios/search?search=${search}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            .then(response => {
                setUsuariosPesquisados(response.data)
            })
            .catch(err => console.log(err))
        } else {
            setUsuariosPesquisados([])
        }

    }, [search])

    return {setSearch, search, usuariosPesquisados}
}

export default useSearchUsuario