//import {Link} from 'react-router-dom';
//import React from "react";
import {useQuery} from "@connectrpc/connect-query";
import {getGroups} from "../gen/com/demo/univer/grpc/group/v1/group-GroupService_connectquery.ts";

export function GroupListPage() {
    const {data, isLoading, error} = useQuery(
        getGroups,
        {});

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error?.code}, {error?.message}</div>;

    return (
        <div>
            <h1>Group list</h1>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
    );
}
