import {Formik, Form, Field, ErrorMessage} from 'formik';
import * as Yup from 'yup';
import {JoinDatePicker} from "./JoinDatePicker.tsx";

export interface CreateStudentFormValues {
    fio: string;
    joinDate: Date;
}

const initialValues: CreateStudentFormValues = {
    fio: '',
    joinDate: new Date(Date.now())
};

const validationSchema = Yup.object({
    fio: Yup.string()
        .required('FIO is required')
        .min(4, 'FIO is too short')
        .max(500, 'FIO is too long'),
    joinDate: Yup.date()
        .required()
});

type CreateStudentFormProps = {
    handleSubmit: (values: CreateStudentFormValues) => Promise<void>
}

export function CreateStudentForm({handleSubmit}: CreateStudentFormProps) {
    return (
        <>
            <h1>Create Student Form</h1>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                {({isSubmitting}) => (
                    <Form>
                        <div>
                            <label htmlFor="fio">FIO</label>
                            <Field type="text" name="fio" id="fio"/>
                            <ErrorMessage name="fio" component="div" className="error"/>
                        </div>

                        <div>
                            <label htmlFor="joinDate">Join Date</label>
                            <Field component={JoinDatePicker} name="joinDate" id="joinDate"/>
                            <ErrorMessage name="joinDate" component="div" className="error"/>
                        </div>

                        <button type="submit" disabled={isSubmitting}>
                            {isSubmitting ? 'Submitting...' : 'Submit'}
                        </button>
                    </Form>
                )}
            </Formik>
        </>
    );
}