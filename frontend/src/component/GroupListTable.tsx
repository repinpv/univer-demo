import type {ColDef, ICellRendererParams} from "ag-grid-community";
import {AgGridReact} from "ag-grid-react";
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import type {ExtGroup} from "../gen/group.ts";
import {Link} from "react-router-dom";

const columnDefs: ColDef<ExtGroup>[] = [
    {field: 'id', headerName: 'Group ID', width: 100},
    {field: 'name', headerName: 'Group Name', filter: true},
    {field: 'memberCount', headerName: 'Member Count', filter: true},
    {
        field: 'id',
        headerName: 'Group Link',
        width: 100,
        cellRenderer: (params: ICellRendererParams<ExtGroup>) => {
            return (
                <Link to={`/group/${params.data?.id}`}>Open</Link>
            )
        }
    },
];

type GroupListTableProps = {
    loading: boolean,
    groupList: ExtGroup[]
}

export function GroupListTable({loading, groupList}: GroupListTableProps) {
    return (
        <div className="ag-theme-alpine" style={{height: 600, width: '100%'}}>
            {loading && (
                <div className="ag-overlay-loading-center">
                    <div>Loading users...</div>
                </div>
            )}

            <AgGridReact
                rowData={groupList}
                columnDefs={columnDefs}
                domLayout='autoHeight'
                pagination={true}
                paginationPageSize={20}
                paginationAutoPageSize={false}
                suppressPaginationPanel={false}
                rowSelection='single'
                overlayLoadingTemplate={
                    '<span class="ag-overlay-loading-center">Loading groups...</span>'
                }
                overlayNoRowsTemplate={
                    '<span style="padding: 10px; border: 2px solid #444; background: lightgoldenrodyellow;">No groups</span>'
                }
            />
        </div>
    );
}