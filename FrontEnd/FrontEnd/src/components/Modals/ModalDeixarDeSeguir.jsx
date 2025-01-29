import { useEffect } from "react"
import "../../styles/Modal.css"

function ModalDeixarDeSeguir({userProfile, removerRelacao, observerDarkMode, setOberserDarkMode}) {

    useEffect(() => {
        setOberserDarkMode(darkMode => !darkMode)
    }, [])

    return (
        <div className="modal fade" id="modalDeixarDeSeguir" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-sm">
                <div className="modal-content rounded-5 dark-m">
                    <div className="modal-body d-flex flex-column rounded-5 p-4">
                        <p className="lead text-center">Você deseja deixar de seguir @{userProfile.username}</p>
                        <button style={{color: 'red'}} className="btn" onClick={removerRelacao} data-bs-dismiss="modal">Deixar de seguir</button>
                        <button className="btn" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalDeixarDeSeguir