function ButtonBack() {

    function back() {
        window.history.back()
    }

    return (
        <div onClick={back} style={{cursor: 'pointer'}} className="d-flex align-items-center mx-2 my-lg-2">
            <ion-icon name="chevron-back-outline"></ion-icon>
        </div>
    )
}

export default ButtonBack