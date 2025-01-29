import foto_perfil from '../../assets/img/profile_photo_default.png'
import { Link } from "react-router-dom"
import { useEffect, useState } from 'react'

import "../../styles/CardUsuarioSearch.css"
import API_BASE_URL from '../../config/apiConfig'
import useRelacao from '../../hooks/useRelacao'

function CardUsuarioSearch({userSearched, user}) {

    const {getCommomRelationships} = useRelacao()
    const [relacoesEmComum, setRelacoesEmComum] = useState([])

    useEffect(() => {
        const fetchRelacoes = async () => {
            const relacoes = await getCommomRelationships(user, userSearched)
            setRelacoesEmComum(relacoes)
        }

        fetchRelacoes()
    }, [user, userSearched])

    return (
        <li className='my-1 dark-m'>
            <div className="w-100">
                <div className="d-flex justify-content-start align-items-center">
                    <Link to={`/perfil/${userSearched.username}`} className="nav-link d-flex">
                        <div className='me-3 d-flex align-items-center justify-content-start'>
                            {userSearched.foto_perfil ? (
                                <img src={`${API_BASE_URL}/img/${userSearched.id}/${userSearched.foto_perfil}`} className='rounded-circle foto_search'/>
                            ) : (
                                <img src={foto_perfil} alt={`${userSearched.nome}`} className="rounded-circle foto_search"/>
                            )}
                        </div>
                        <div className="d-flex flex-column">
                            <p className="lead my-0 username">{userSearched.username}</p>
                            <p className="my-0 name">{userSearched.nome}</p>
                            {relacoesEmComum && relacoesEmComum.length > 0 && (
                                <p className="text-secondary text-truncate w-100" style={{fontSize: '10px'}}>
                                    Seguido por {relacoesEmComum.length} {relacoesEmComum.length == 1 ? 'usuário' : 'usuários'} em comum
                                </p>
                            )}
                        </div>
                    </Link>
                </div>
            </div>
        </li>
    )
}

export default CardUsuarioSearch