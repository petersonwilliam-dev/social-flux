import useSearchUsuario from "../../hooks/useSearchUsuario"
import CardUsuarioSearch from "../../components/CardUsuarioSearch/CardUsuarioSearch"
import ButtonBack from '../../components/ButtonBack/ButtonBack'

function SearchUser({user}) {

    const {search, setSearch, usuariosPesquisados} = useSearchUsuario()

    return (
        <div className="search-user">
            <div className="search-bar me-2 d-flex align-items-center">
                <ButtonBack />
                <input type="text" className="form-control" id="search" placeholder="Faça sua pesquisa" onChange={(e) => setSearch(e.target.value)}/>
            </div>
            <hr />
            <div className="mx-2 text-break">
                <ul className="nav nav-pills flex-column">
                    {search.length > 0 && usuariosPesquisados.length == 0 ? (
                        <div>
                            <p className="lead text-center" style={{fontSize: '15px'}}>
                                Não foi encontrado nenhum usuário com '{search}'
                            </p>
                        </div>
                    ): (
                        <>
                            {usuariosPesquisados.map((usuario, index) => (
                                <CardUsuarioSearch userSearched={usuario} user={user} key={index} />
                            ))}
                        </>
                    )}
                </ul>
            </div>
        </div>
    )
}

export default SearchUser