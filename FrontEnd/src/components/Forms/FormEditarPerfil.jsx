import { Formik, Form, Field, ErrorMessage } from 'formik'
import * as Yup from 'yup'

function FormEditarPerfil({editarPerfil, user}) {

    const validationSchema = Yup.object({
        id: Yup.number().required(),
        username: Yup.string().required("Campo obrigatório"),
        nome: Yup.string().required("Campo obrigatório"),
        biografia : Yup.string(),
        privado: Yup.boolean()
    })

    return (
        <div className="form px-3">
            <Formik
            initialValues={{
                id: user.id,
                username: user.username,
                nome: user.nome,
                biografia: user.biografia ? user.biografia : '',
                privado: user.privado
            }}
            validationSchema={validationSchema}
            onSubmit={(values) => {
                editarPerfil(values)
            }}
            >
                <Form>
                    <Field type="hidden" name="id"/>
                    <div className="form-floating mb-4">
                        <p className="lead">Username</p>
                        <Field type="text" name="username" className="form-control" id="username" placeholder="Username"/>
                        <ErrorMessage name="username">{msg => (<div className="text-danger">{msg}</div>)}</ErrorMessage>
                    </div>
                    <div className="form-floating mb-4">
                        <p className="lead">Nome</p>
                        <Field type="text" name="nome" className="form-control" id="nome"/>
                        <ErrorMessage name="nome">{msg => (<div className="text-danger">{msg}</div>)}</ErrorMessage>
                    </div>
                    <div className="form-floating mb-4">
                        <p className="lead">Biografia</p>
                        <Field as="textarea" name="biografia" className="form-control" id="biografia" style={{height: '150px'}}/>
                        <ErrorMessage name="biografia">{msg => (<div className="text-danger">{msg}</div>)}</ErrorMessage>
                    </div>
                    <div className="form-check">
                        <Field className="form-check-input" name="privado" type="checkbox" id="flexCheckDefault"/>
                        <label className="form-check-label" htmlFor="flexCheckDefault">
                            Conta privada
                        </label>
                    </div>
                    <div className="mt-4">
                        <button type="submit" className="btn btn-primary">Editar</button>
                    </div>
                </Form>
            </Formik>
        </div>
    )
}

export default FormEditarPerfil