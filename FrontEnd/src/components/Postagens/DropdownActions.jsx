import { Link } from "react-router-dom"
import ModalExcluirPostagem from "../Modals/ModalExcluirPostagem"

function DropdownActions({postagem, excluirPostagem}) {
    return (
        <>
            <div className="dropdawn">
                <ion-icon style={{cursor: 'pointer'}} data-bs-toggle="dropdown" aria-expanded="false" name="ellipsis-vertical-outline"></ion-icon>
                <ul className="dropdown-menu p-3">
                    <li>
                        <Link className="d-flex align-items-center link-danger text-decoration-none" data-bs-toggle="modal" data-bs-target="#modalExcluirPostagem"><ion-icon name="trash-outline"></ion-icon> Excluir postagem</Link>
                    </li>
                </ul>
            </div>
            <ModalExcluirPostagem postagem={postagem} excluirPostagem={excluirPostagem}/>
        </>
    )
}

export default DropdownActions