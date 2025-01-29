import { Formik, Form, Field } from "formik"
import * as Yup from 'yup'
import API_BASE_URL from "../../config/apiConfig"
import '../../styles/FormComentar.css'
import ModalImagemComentario from "../Modals/ModalImagemComentario"

function FormComentar({user, postagem, criarPostagem}) {
    

    const validationSchema = Yup.object({
        legenda: Yup.string(),
        imagem: Yup.mixed()
    })

    return (
        <Formik
        validationSchema={validationSchema}
        initialValues={{
            legenda: '',
            imagem: ''
        }}
        onSubmit={(values) => {
            criarPostagem(values, postagem, user)
        }}>
            {({setFieldValue, errors, touched}) => (
                <Form className="dark-m">
                    <div className="form-floating d-flex mb-2">
                        <div className="me-2">
                            <img src={`${API_BASE_URL}/img/${user.id}/${user.foto_perfil}`} className="rounded-circle" style={{width: '30px'}} />
                        </div>
                        <Field as="textarea" name="legenda" className="input-comentario" placeholder="Insira um comentário" id="legenda" style={{height: '100px'}} />
                    </div>
                    <div className="d-flex w-100 justify-content-between">
                        <div className="d-flex justify-content-center align-items-center" style={{cursor: 'pointer'}} data-bs-toggle="modal" data-bs-target="#modalImagemComentario">
                            <ion-icon name="image-outline"></ion-icon>
                        </div>
                        <div>
                            <button type="submit" className="btn btn-primary">Comentar</button>
                        </div>
                    </div>
                    <ModalImagemComentario setFieldValue={setFieldValue} errors={errors} touched={touched} />
                </Form>
            )}
        </Formik>
    )
}

export default FormComentar