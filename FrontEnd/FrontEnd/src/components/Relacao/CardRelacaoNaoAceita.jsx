import { Link } from 'react-router-dom'
import API_BASE_URL from '../../config/apiConfig'

function CardRelacaoNaoAceita({relacao, aceitarRelacao, removerRelacao}) {
    return (
        <div className="d-flex justify-content-between dark-m w-100">
            <div className="d-flex justify-content-start align-items-center">
                <Link to={`/perfil/${relacao.seguidor.username}`} className="nav-link d-flex justify-content-start align-items-center">
                    {relacao.seguidor.foto_perfil ? (
                        <img src={`${API_BASE_URL}/img/${relacao.seguidor.id}/${relacao.seguidor.foto_perfil}`} style={{width: '30px', marginRight: '5px'}} className='rounded-circle'/>
                    ) : (
                        <img src={foto_perfil} alt={`${relacao.seguidor.nome}`} style={{width: '30px', marginRight: '5px'}} className='rounded-circle'/>
                    )}
                    {relacao.seguidor.username}
                </Link>
            </div>
            <div className="actions d-flex">
                <Link onClick={() => aceitarRelacao(relacao.id, relacao.seguido, relacao.seguidor)} className="mx-1 d-flex justify-content-center align-items-center"><ion-icon style={{color: 'green'}} name="checkmark-outline"></ion-icon></Link>
                <Link onClick={() => removerRelacao(relacao.id)} className="mx-1 d-flex justify-content-center align-items-center "><ion-icon style={{color: 'red'}} name="close-outline"></ion-icon></Link>
            </div>
        </div>
    )
}

export default CardRelacaoNaoAceita