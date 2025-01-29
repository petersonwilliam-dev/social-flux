import CardRelacaoNaoAceita from "./CardRelacaoNaoAceita"

function ListaRelacoesNaoAceitas({relacoesNaoAceitas, aceitarRelacao, removerRelacao}) {
    return (
        <ul className="nav nav-pills flex-column mt-1">
            {relacoesNaoAceitas.length > 0 ? (
                <>
                    {relacoesNaoAceitas.map((relacao, index) => (
                        <li key={index}>
                            <CardRelacaoNaoAceita relacao={relacao} aceitarRelacao={aceitarRelacao} removerRelacao={removerRelacao}/>
                        </li>
                    ))}
                </>
            ) : (
                <div className="text-center px-3">
                    <p className="lead">Não há solicitações de amizade</p>
                </div>
            )}
        </ul>
    )
}

export default ListaRelacoesNaoAceitas