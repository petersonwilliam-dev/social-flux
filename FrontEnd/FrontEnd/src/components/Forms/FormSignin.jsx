import { Formik, Form, Field, ErrorMessage } from 'formik'
import * as Yup from 'yup'

function FormSignin({toggleForm, buscarUsuario, erro}) {

    const validationSchema = Yup.object().shape({
        email: Yup.string().required("Preencha o campo de email").email("Endereço de email inválido"),
        senha: Yup.string().required("Preencha o campo de senha").min(8, "A senha deve conter no mínimo 8 caracteres")
    })


    return (
        <div className="w-100" style={{maxWidth: '500px'}}>
            <div className="text-center"><h3 className="display-4 mb-5">Login</h3></div>
            {erro && (
                <div className='alert alert-danger'>
                    <p className="text-danger">{erro.erro}</p>
                </div>
            )}
            <Formik
            initialValues={{email: '', senha: ''}}
            validationSchema={validationSchema}
            onSubmit={(values) => {
                buscarUsuario(values)
            }}
            >
                {() => (
                    <Form>
                        <div className="form-floating mb-4">
                            <Field type="text" name="email" className="form-control" id="email"/>
                            <label htmlFor="email">Email</label>
                            <ErrorMessage name="email">
                                {msg => (<div className="text-danger">{msg}</div>)}
                            </ErrorMessage>
                        </div>
                        <div className="form-floating mb-4">
                            <Field type="password" name="senha" className="form-control" id="senha"/>
                            <label htmlFor="senha">Senha</label>
                            <ErrorMessage name="senha">
                                {msg => (<div className="text-danger">{msg}</div>)}
                            </ErrorMessage>
                        </div>
                        <button type="submit" className="btn btn-primary" >Enviar</button>
                    </Form>
                )}
            </Formik>
            <div className="mt-3">
                <p className="text-primary"><a href="#" onClick={toggleForm}>Não tem conta?</a></p>
                <p className="text-primary"><a href="#">Esqueci minha senha</a></p>
            </div>
        </div>
    )
}

export default FormSignin