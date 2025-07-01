import {Formik, Form, Field, ErrorMessage} from 'formik';
import * as Yup from 'yup';

export interface CreateGroupFormValues {
    name: string;
}

const initialValues: CreateGroupFormValues = {
    name: ''
};

const validationSchema = Yup.object({
    name: Yup.string()
        .required('Name is required')
        .min(2, 'Name is too short')
        .max(50, 'Name is too long')
    //.matches(),
});

type CreateGroupFormProps = {
    handleSubmit: (values: CreateGroupFormValues) => Promise<void>
}

export function CreateGroupForm({handleSubmit}: CreateGroupFormProps) {
    return (
        <>
            <h2>Create Group Form</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                {({isSubmitting}) => (
                    <Form>
                        <div>
                            <label htmlFor="name">Group Name</label>
                            <Field type="text" name="name" id="name"/>
                            <ErrorMessage name="name" component="div" className="error"/>
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