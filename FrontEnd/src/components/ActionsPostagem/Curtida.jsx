import { useState } from "react"
import useCurtida from "../../hooks/useCurtida"
import { useEffect } from "react"
import useNotification from "../../hooks/useNotification"
import Toasts from "../Toasts/Toasts"

function Curtida({user, postagem, setNumeroCurtidas}) {

    const [curtida, setCurtida] = useState(null)
    const {criarCurtida, buscarCurtida, removerCurtida, buscarNumeroCurtidas, message, setMessage} = useCurtida()

    useEffect(() => {
        const fetchCurtida = async () => {
            const curtida = await buscarCurtida(user, postagem)
            setCurtida(curtida)
        }

        const fetchNumeroCurtida = async () => {
            const numeroCurtidas = await buscarNumeroCurtidas(postagem)
            setNumeroCurtidas(numeroCurtidas)
        }

        fetchNumeroCurtida()
        fetchCurtida()
    }, [postagem])



    function curtir() {
        const curtir = async () => {
            const curtida = await criarCurtida(user, postagem)
            setCurtida(curtida)
            setNumeroCurtidas(prev => prev + 1)
        }

        curtir()

    }

    

    return (
        <>
            {message && (
                <Toasts msg={message.mensagem} type="danger" />
            )}
            {curtida ? (
                <div onClick={() => removerCurtida(curtida, setCurtida, setNumeroCurtidas)} style={{cursor: 'pointer'}} className="text-dark text-start me-2 d-flex align-items-center">
                    <ion-icon style={{color: 'red', marginRight: '1px'}} name="heart-outline"></ion-icon>
                </div>
            ) : (
                <div onClick={curtir} style={{cursor: 'pointer'}} className="text-dark text-start me-2 d-flex align-items-center">
                    <ion-icon style={{marginRight: '5px'}} name="heart-outline"></ion-icon>
                </div>
            )}
        </>
    )
}

export default Curtida