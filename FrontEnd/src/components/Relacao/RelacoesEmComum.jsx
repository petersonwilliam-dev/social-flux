import API_BASE_URL from "../../config/apiConfig"
import foto from "../../assets/img/profile_photo_default.png"
import ModalListUsers from "../Modals/ModalListUsers"

import { useState, useEffect } from "react"
import useRelacao from "../../hooks/useRelacao"
import Toasts from "../Toasts/Toasts"

function RelacoesEmComum({user, profileUser, setObserverDarkMode}) {

    const [relacoesEmComum, setRelacoesEmComum] = useState([])
    const {getCommomRelationships, message} = useRelacao()

    useEffect(() => {
        const fetchRelacoes = async () => {
            const relacoes = await getCommomRelationships(user, profileUser)
            setRelacoesEmComum(relacoes)
        }

        fetchRelacoes()
    }, [user, profileUser])


    return (
        <div>
            {message && (
                <Toasts mensagem={message} />
            )}
            {relacoesEmComum && relacoesEmComum.length > 0 && (
                <div className="d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#relacoesEmComum" style={{cursor: 'pointer'}}>
                    <div className="fotos me-2">
                        {relacoesEmComum.slice(0, 3).map((usuario, index) => (
                            <div key={index}>
                                {usuario.foto_perfil ? (
                                    <img key={index} src={`${API_BASE_URL}/img/${usuario.id}/${usuario.foto_perfil}`} alt="" className="rounded-circle foto-relacao-em-comum"/>
                                ) : (
                                    <img key={index} src={foto} alt="" className="rounded-circle foto-relacao-em-comum"/>
                                )}
                            </div>
                        ))}
                    </div>
                    <div className="d-flex align-items-center w-100 text-break">
                        <p className="lead relacao-em-comum mb-0">
                            Seguido por {relacoesEmComum.slice(0 , 3).map((usuario, index) => (<span key={index}>{usuario.username}{index < 2 && relacoesEmComum[index + 1] ? ', ' : ' '}</span>))}
                            {relacoesEmComum.length > 3 && `e por mais ${relacoesEmComum.length - 3} pessoas`}
                        </p>
                    </div>
                </div>
            )}
            <ModalListUsers title="Relações em comum" msgDefault="Não há relações em comum" id="relacoesEmComum" users={relacoesEmComum} setObserverDarkMode={setObserverDarkMode} />
        </div>
    )
}

export default RelacoesEmComum