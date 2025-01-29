import '../../styles/ContainerMain.css'

function ContainerMain({children}) {
    return (
        <div className="container-main mb-5">
            <main className="main dark-m">
                {children}
            </main>
        </div>
    )
}

export default ContainerMain