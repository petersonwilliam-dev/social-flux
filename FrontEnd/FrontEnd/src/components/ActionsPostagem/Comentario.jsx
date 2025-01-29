function Comentario({showFormularioComentar}) {

    return(
        <>
            <div className="text-dark text-start me-2 d-flex align-items-center" onClick={showFormularioComentar} style={{cursor: 'pointer'}}>
                <ion-icon style={{marginRight: '5px'}} name="chatbubble-outline"></ion-icon>
            </div>

        </>
    )
}

export default Comentario