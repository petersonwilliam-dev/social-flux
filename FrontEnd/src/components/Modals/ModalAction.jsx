import { useEffect } from "react"
import "../../styles/Modal.css"

function Modal({title, titleAction, action, colorText, setOberserDarkMode, id}) {

    useEffect(() => {
        setOberserDarkMode(darkMode => !darkMode)
    }, [])

    return (
        <div className="modal fade" id={id} data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-sm">
                <div className="modal-content rounded-5 dark-m">
                    <div className="modal-body d-flex flex-column rounded-5 p-4">
                        <p className="lead text-center">{title}</p>
                        <button style={{color: `${colorText}`}} className="btn" onClick={action} data-bs-dismiss="modal">{titleAction}</button>
                        <button className="btn" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Modal