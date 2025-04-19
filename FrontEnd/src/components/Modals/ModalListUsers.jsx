import { Link } from "react-router-dom"
import img from "../../assets/img/profile_photo_default.png"
import "../../styles/Modal.css"
import API_BASE_URL from "../../config/apiConfig"

function ModalListUsers({id, title, users, msgDefault}) {

    return (
        <div className="modal fade" id={id} tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content dark-m">
                    <div className="modal-header">
                        <p className="display-5">{title}</p>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        {users.length > 0 ? (
                            <div>
                                {users.map((user, index) => (
                                    <div key={index} className="my-3">
                                        <Link to={`/perfil/${user.username}`} className="d-flex align-items-center nav-link">
                                            <div data-bs-dismiss="modal">
                                                {user.foto_perfil ? (
                                                    <img src={`${API_BASE_URL}/img/${user.id}/${user.foto_perfil}`} className="rounded-circle" style={{width: '50px', marginRight: '20px'}}/>
                                                ) : (
                                                    <img src={img} alt="" style={{width: '50px', marginRight: '20px'}}/>
                                                )}
                                                {user.username}
                                            </div>
                                        </Link>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="text-center">
                                <p className="lead">{msgDefault}</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalListUsers