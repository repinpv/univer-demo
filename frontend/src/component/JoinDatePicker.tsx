import type {FieldProps} from 'formik';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

interface JoinDatePickerProps extends FieldProps {
    placeholder?: string;
    dateFormat?: string;
    maxDate?: Date;
    minDate?: Date;
}

export function JoinDatePicker({
                                   field,
                                   form,
                                   placeholder = 'Select join date',
                                   dateFormat = 'dd.MM.yyyy',
                                   maxDate,
                                   minDate
                               }: JoinDatePickerProps) {
    const {name, value} = field;
    const {setFieldValue} = form;

    return (
        <DatePicker
            id={name}
            {...field}
            selected={(value && new Date(value)) || null}
            onChange={(date: Date | null) => setFieldValue(name, date)}
            placeholderText={placeholder}
            dateFormat={dateFormat}
            className="form-control"
            maxDate={maxDate}
            minDate={minDate}
        />
    );
}