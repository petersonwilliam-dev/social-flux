import { Formik, Form, Field } from "formik"
import * as Yup from "yup"

function FormCriarPostagem({criarPostagem, user}) {

    const validationSchema = Yup.object({
        legenda: Yup.string(),
        imagem: Yup.mixed(),
    })

    return (
        <Formik
            validationSchema={validationSchema}
            initialValues={{
                legenda: '',
                imagem: ''
            }}
            onSubmit={(values) => {
                criarPostagem(values, null, user)
            }}>
                {({setFieldValue, errors, touched}) => (
                    <Form >
                        <div className="my-4">
                            <label htmlFor="imagem" className="mb-2">Imagem da postagem</label>
                            <input className="form-control" type="file" name="imagem" id="imagem" onChange={(event) => setFieldValue("imagem", event.currentTarget.files[0])} />
                            {errors.imagem && touched.imagem ? (
                                <div className="text-danger">{errors.imagem}</div>
                            ) : null}
                        </div>
                        <div className="form-floating mb-4">
                            <Field as="textarea" name="legenda" className="form-control" id="legenda" style={{height: '150px'}} />
                        </div>
                        <div className="mt-3">
                            <button className="btn btn-primary" type="submit">Postar</button>
                        </div>
                    </Form>
                )}
        </Formik>
    )
}

export default FormCriarPostagem