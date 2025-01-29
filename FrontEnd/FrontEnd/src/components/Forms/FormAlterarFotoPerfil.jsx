import { Formik, Form } from 'formik'
import * as Yup from 'yup'

function FormAlterarFotoPerfil({atualizarFotoPerfil}) {
    
    const validationSchemaFotoPerfil = Yup.object({
        foto_perfil: Yup.mixed().required("Arquivo é necessário")
    })

    return (
        <Formik
        initialValues={{foto_perfil: null}}
        validationSchema={validationSchemaFotoPerfil}
        onSubmit={(values) => {
            atualizarFotoPerfil(values)
        }}>
            {({setFieldValue, errors, touched}) => (
                <Form className='dark-m'>
                    <div className="my-4">
                        <input className="form-control" type="file" name="foto_perfil" id="formFile" onChange={(event) => setFieldValue("foto_perfil", event.currentTarget.files[0])} />
                        {errors.foto_perfil && touched.foto_perfil ? (
                        <div className="text-danger">{errors.foto_perfil}</div>
                        ) : null}
                    </div>
                    <div className="text-center">
                        <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Enviar</button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}

export default FormAlterarFotoPerfil