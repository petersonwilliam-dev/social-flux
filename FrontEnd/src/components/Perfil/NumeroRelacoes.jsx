function NumeroRelacoes({relacao, numero, id}) {
    return (
        <div className="follow text-center mx-2 mx-lg-4" data-bs-toggle="modal" data-bs-target={id}>
            <p className="h4 ajustavel-6">{numero}</p>
            <p className="lead ajustavel-6">{relacao}</p>
        </div>
    )
}

export default NumeroRelacoes