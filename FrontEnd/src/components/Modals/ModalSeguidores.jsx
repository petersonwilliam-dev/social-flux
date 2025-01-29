import { Link } from "react-router-dom"
import img from "../../assets/img/profile_photo_default.png"
import "../../styles/Modal.css"

function ModalSeguidoresPerfil({seguidores}) {

    return (
        <div className="modal fade" id="seguidores" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content dark-m">
                    <div className="modal-header">
                        <p className="display-5">Seguidores</p>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        {seguidores.length > 0 ? (
                            <div>
                                {seguidores.map((seguidor, index) => (
                                    <div key={index} className="my-2">
                                        <Link to={`/perfil/${seguidor.seguidor.username}`} className="d-flex align-items-center nav-link">
                                            <div data-bs-dismiss="modal">
                                                {seguidor.seguidor.foto_perfil ? (
                                                    <img src={`http://localhost:8000/img/${seguidor.seguidor.id}/${seguidor.seguidor.foto_perfil}`} className="rounded-circle" style={{width: '50px', marginRight: '20px'}}/>
                                                ) : (
                                                    <img src={img} alt="" style={{width: '50px', marginRight: '20px'}}/>
                                                )}
                                                {seguidor.seguidor.username}
                                            </div>
                                        </Link>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="text-center">
                                <p className="lead">Não há seguidores</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalSeguidoresPerfil