import "../../styles/Modal.css"

function ModalImagemComentario({setFieldValue, errors, touched}) {
    return (
        <div className="modal fade" id="modalImagemComentario" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content dark-m">
                    <div className="modal-body">
                        <div className="d-flex justify-content-end">
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="my-4">
                            <input className="form-control" type="file" name="imagem" id="formFile" onChange={(event) => setFieldValue("imagem", event.currentTarget.files[0])} />
                            {errors.imagem && touched.foto_perfil ? (
                                <div className="text-danger">{errors.imagem}</div>
                            ) : null}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalImagemComentario