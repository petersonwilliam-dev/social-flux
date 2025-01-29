import foto_perfil from '../../assets/img/profile_photo_default.png'
import { Link } from "react-router-dom"
import ListaRelacoesNaoAceitas from './ListaRelcoesNaoAceitas'

function ListaAmizadesNaoAceitas({relacoesNaoAceitas, showSidebarMain, aceitarRelacao, removerRelacao}) {

    return (
        <div className="mb-auto">
            <div className="text-start w-100 mb-4 ps-3">
                <Link onClick={showSidebarMain} className="nav-link d-flex justify-content-start align-items-center"><ion-icon name="chevron-back-outline"></ion-icon>Voltar</Link>
            </div>
            <ListaRelacoesNaoAceitas relacoesNaoAceitas={relacoesNaoAceitas} aceitarRelacao={aceitarRelacao} removerRelacao={removerRelacao} />
        </div>
    )
}

export default ListaAmizadesNaoAceitas