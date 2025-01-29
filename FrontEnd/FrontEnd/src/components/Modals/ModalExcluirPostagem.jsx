import "../../styles/Modal.css"

function ModalExcluirPostagem({postagem, excluirPostagem}) {
    return (
        <div className="modal fade" id="modalExcluirPostagem" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-sm">
                <div className="modal-content rounded-5">
                    <div className="modal-body text-center d-flex flex-column p-4">
                        <p className="lead">Deseja excluir a postagem</p>
                        <button style={{color: 'red'}} className="btn" onClick={() => excluirPostagem(postagem.id)} data-bs-dismiss="modal">Excluir postagem</button>
                        <button className="btn" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalExcluirPostagem