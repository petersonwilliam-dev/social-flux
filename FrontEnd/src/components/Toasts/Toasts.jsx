import { useRef, useEffect } from "react"

function Toasts({msg, type}) {

    const toastRef = useRef(null)

    useEffect(() => {
        const toastElement = toastRef.current;
        if (toastElement) {
            const toast = new bootstrap.Toast(toastElement)
            toast.show()
        }
    }, [])

    return (
        <div ref={toastRef} className={`toast align-items-center text-bg-${type} border-0 position-fixed top-0 end-0`} role="alert" data-bs-delay="10000" aria-live="assertive" aria-atomic="true">
            <div className="d-flex">
                <div className="toast-body">
                    {msg}
                </div>
                <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    )
}

export default Toasts