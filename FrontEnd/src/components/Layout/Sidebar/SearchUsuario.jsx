import { Link } from "react-router-dom"
import CardUsuarioSearch from "../../CardUsuarioSearch/CardUsuarioSearch"

function SearchUsuario({showSidebarMenu, usuariosPesquisados, setSearch, search, user}) {
    return (
        <div className="mb-auto">
            <div className="text-start w-100 mb-4 ps-3">
                <Link onClick={showSidebarMenu} className="nav-link d-flex justify-content-start align-items-center"><ion-icon name="chevron-back-outline"></ion-icon>Voltar</Link>
            </div>
            <div className="search">
                <div className="mb-4 px-3">
                    <input type="text" name="search" className="form-control" id="search" onChange={(event) => setSearch(event.target.value)}/>
                </div>
            </div>
            <div className="resultadoPesquisa px-2">
                <ul className="nav nav-pills flex-column">
                    {search.length > 0 && usuariosPesquisados.length == 0 ? (
                        <div className="w-100">
                            <p className="lead text-center text-break" style={{fontSize: '15px'}}>
                                Não foi encontrado nenhum usuário com '{search}'
                            </p>
                        </div>
                    ): (
                        <>
                            {usuariosPesquisados.map((usuario, index) => (
                                <CardUsuarioSearch userSearched={usuario} user={user} key={index}/>
                            ))}
                        </>
                    )}
                </ul>
            </div>
        </div>
    )
}

export default SearchUsuario