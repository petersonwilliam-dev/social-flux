import '../../styles/ContainerMain.css'

function ContainerMain({children}) {
    return (
        <div className="container-main mb-5">
            <main className="main mt-3 mt-lg-5 dark-m">
                {children}
            </main>
        </div>
    )
}

export default ContainerMain