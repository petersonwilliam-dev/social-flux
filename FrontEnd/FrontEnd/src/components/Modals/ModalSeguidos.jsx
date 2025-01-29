import { Link } from "react-router-dom"
import img from "../../assets/img/profile_photo_default.png"
import "../../styles/Modal.css"

function ModalSeguidosPerfil({seguidos}) {

    return (
        <div className="modal fade" id="seguidos" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content dark-m">
                    <div className="modal-header">
                        <p className="display-5">Seguidos</p>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        {seguidos.length > 0 ? (
                            <div>
                                {seguidos.map((seguido, index) => (
                                    <div key={index} className="my-3">
                                        <Link to={`/perfil/${seguido.seguido.username}`} className="d-flex align-items-center nav-link">
                                            <div data-bs-dismiss="modal">
                                                {seguido.seguido.foto_perfil ? (
                                                    <img src={`http://localhost:8000/img/${seguido.seguido.id}/${seguido.seguido.foto_perfil}`} className="rounded-circle" style={{width: '50px', marginRight: '20px'}}/>
                                                ) : (
                                                    <img src={img} alt="" style={{width: '50px', marginRight: '20px'}}/>
                                                )}
                                                {seguido.seguido.username}
                                            </div>
                                        </Link>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="text-center">
                                <p className="lead">Não há seguidos</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalSeguidosPerfil