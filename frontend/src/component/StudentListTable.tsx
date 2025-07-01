import moment from 'moment';
import type {ColDef, ICellRendererParams} from "ag-grid-community";
import {AgGridReact} from "ag-grid-react";
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import type {Student} from "../gen/student.ts";
import {Timestamp} from "../gen/google/protobuf/timestamp.ts";

export type OnStudentDelete = (studentId?: bigint) => void;

type StudentListTableProps = {
    loading: boolean,
    studentList: Student[],
    onStudentDelete: OnStudentDelete
}

export function StudentListTable({loading, studentList, onStudentDelete}: StudentListTableProps) {
    const columnDefs: ColDef<Student>[] = [
        {field: 'id', headerName: 'Student ID', width: 100},
        {field: 'fio', headerName: 'FIO', filter: true},
        {
            field: 'joinDate',
            headerName: 'Join Date',
            filter: true,
            cellRenderer: (params: ICellRendererParams<Student>) => {
                const joinDate = Timestamp.toDate(params.data?.joinDate || Timestamp.now());
                const formatted: string = moment(joinDate).format('DD.MM.YYYY');
                return (
                    <span>{formatted}</span>
                )
            }
        },
        {
            field: 'id',
            headerName: 'Delete',
            filter: false,
            cellRenderer: (params: ICellRendererParams<Student>) => {
                return (
                    <a onClick={() => onStudentDelete(params.data?.id)}>Delete</a>
                )
            }
        }
    ];

    return (
        <div className="ag-theme-alpine" style={{height: 300, width: 750}}>
            {loading && (
                <div className="ag-overlay-loading-center">
                    <div>Loading users...</div>
                </div>
            )}

            <AgGridReact
                rowData={studentList}
                columnDefs={columnDefs}
                domLayout='autoHeight'
                pagination={true}
                paginationPageSize={20}
                paginationAutoPageSize={false}
                suppressPaginationPanel={false}
                rowSelection='single'
                overlayLoadingTemplate={
                    '<span class="ag-overlay-loading-center">Loading students...</span>'
                }
                overlayNoRowsTemplate={
                    '<span style="padding: 10px; border: 2px solid #444; background: lightgoldenrodyellow;">No students</span>'
                }
            />
        </div>
    );
}