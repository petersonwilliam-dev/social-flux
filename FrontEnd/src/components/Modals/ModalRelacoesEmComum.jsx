import { Link } from "react-router-dom"
import img from "../../assets/img/profile_photo_default.png"
import "../../styles/Modal.css"

function ModalRelacoesEmComum({relacoesEmComum}) {
    return (
        <div className="modal fade" id="relacoesEmComum" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content dark-m">
                    <div className="modal-header">
                        <p className="display-5">Em comum</p>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <div>
                            {relacoesEmComum && (
                                <>
                                    {relacoesEmComum.map((usuario, index) => (
                                        <div key={index} className="my-2">
                                            <Link to={`/perfil/${usuario.username}`} className="d-flex align-items-center nav-link">
                                                <div data-bs-dismiss="modal">
                                                    {usuario.foto_perfil ? (
                                                        <img src={`http://localhost:8000/img/${usuario.id}/${usuario.foto_perfil}`} className="rounded-circle" style={{width: '50px', marginRight: '20px'}}/>
                                                    ) : (
                                                        <img src={img} alt="" style={{width: '50px', marginRight: '20px'}}/>
                                                    )}
                                                    {usuario.username}
                                                </div>
                                            </Link>
                                        </div>
                                    ))}
                                </>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalRelacoesEmComum