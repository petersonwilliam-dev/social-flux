import { Formik, Form, Field, ErrorMessage } from 'formik'
import * as Yup from 'yup'
import { subYears } from 'date-fns'

function FormSignup({toggleForm, adicionarUsuario, erro}) {

    const validationSchema = Yup.object({
        email: Yup.string().email("Email inválido").required("Campo obrigatório"),
        telefone: Yup.string().required("Campo obrigatório").min(11, "Escreva o número de telefone no formato XX XXXXX-XXXX"),
        sexo: Yup.string().required("Selecione uma opção"),
        data_nascimento: Yup.date().required("Campo obrigatório").max(subYears(new Date(), 18), "Você deve ter no mínimo 18 anos").typeError("Data inválida"),
        nome: Yup.string().required("Campo obrigatório"),
        username : Yup.string().required("Campo obrigatório"),
        senha: Yup.string().required("Campo obrigatório").min(8, "A senha deve conter no mínimo 8 caracteres"),
        confirmarSenha: Yup.string().required("Campo obrigatório").oneOf([Yup.ref('senha'), null], "As senhas não coincidem")
    })

    return (
        <div className="w-100" style={{maxWidth: '500px'}}>
            <div className="text-center"><h3 className="display-4 mb-5">Criar Conta</h3></div>
            <div className="row g-0">
                {erro && (
                    <div className='alert alert-danger'>
                        <p className="text-danger">{erro.erro}</p>
                    </div>
                )}
                <Formik
                initialValues={{
                    email: "",
                    telefone: "",
                    sexo: "",
                    data_nascimento: "",
                    nome: "",
                    username: "",
                    senha: "",
                    confirmarSenha: ""
                }}
                validationSchema={validationSchema}
                onSubmit={(values) => {
                    adicionarUsuario(values)
                }}
                >
                    {({isSubmitting}) => (
                        <Form>
                            <div className="mt-2 mb-2">
                                <p className="mb-4 lead">Dados pessoais</p>
                                <div className="form-floating mb-4">
                                    <Field type="email" name="email" className="form-control" id="email"/>
                                    <label htmlFor="email">Email</label>
                                    <ErrorMessage name='email'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                                <div className="form-floating mb-4">
                                    <Field type="text" name="telefone" className="form-control" id="telefone"/>
                                    <label htmlFor="telefone">Telefone</label>
                                    <ErrorMessage name='telefone'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                                <div className="form-floating mb-4">
                                    <div>
                                        <p className='lead'>Sexo</p>
                                        <div className="form-check form-check-inline">
                                            <label htmlFor="sexo" className="form-check-label"><Field className="form-check-input" type="radio" name='sexo' value="F"/>Feminino</label>
                                        </div>
                                        <div className="form-check form-check-inline">
                                            <label className="form-check-label"><Field className="form-check-input" type="radio" name='sexo' value="M" />Masculino</label>
                                        </div>
                                        <ErrorMessage name='sexo'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                    </div>
                                </div>
                                <div className="form-floating mb-4">
                                    <Field type="date" name="data_nascimento" className="form-control" id="data_nascimento"/>
                                    <label htmlFor="data_nascimento">Data nascimento</label>
                                    <ErrorMessage name='data_nascimento'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                            </div>
                            
                            <div className="mt-2 mb-2">
                                <p className="lead mb-4">Dados da conta</p>
                                <div className="form-floating mb-4">
                                    <Field type="text" name="nome" className="form-control" id="nome"/>
                                    <label htmlFor="nome">Nome</label>
                                    <ErrorMessage name='nome'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                                <div className="form-floating mb-4">
                                    <Field type="text" name="username" className="form-control" id="username"/>
                                    <label htmlFor="username">Username</label>
                                    <ErrorMessage name='username'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                                <div className="form-floating mb-4">
                                    <Field type="password" name="senha" className="form-control" id="senha"/>
                                    <label htmlFor="senha">Senha</label>
                                    <ErrorMessage name='senha'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                                <div className="form-floating mb-4">
                                    <Field type="password" name="confirmarSenha" className="form-control" id="confirmarSenha"/>
                                    <label htmlFor="confirmarSenha">Confirme a senha</label>
                                    <ErrorMessage name='confirmarSenha'>{msg => (<div className='text-danger'>{msg}</div>)}</ErrorMessage>
                                </div>
                            </div>
        
                            <button type="submit" className="btn btn-primary">Enviar</button>
                        </Form>
                    )}
                </Formik>
                <div className="mt-3">
                    <p className="text-primary"><a href="#" onClick={toggleForm}>Já possui conta?</a></p>
                </div>
            </div>
        </div>
    )
}

export default FormSignup