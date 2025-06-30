//import {Link} from 'react-router-dom';
//import React from "react";

import {useEffect, useState} from "react";
import type {ExtGroup} from "../gen/group.ts";
import {GetGroupsRequest} from "../gen/group.ts";
import {groupServiceClient} from "../grpc.ts";

export function GroupListPage() {
    const [groupList, setGroupList] = useState<ExtGroup[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    useEffect(() => {
        setLoading(true); // Set loading state to true before fetching

        const request = GetGroupsRequest.create();
        groupServiceClient.getGroups(request)
            .then(response => {
                setGroupList(response.response.group);
                console.error(response.response.group);
            })
            .catch(err => {
                console.error(err);
                setError(err);
            })
            .finally(() => setLoading(false))

    }, []);


    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {JSON.stringify(error, null, 2)}</div>;

    return (
        <div>
            <h1>Group list</h1>
            <pre>{JSON.stringify(groupList.map(value => {
                return {
                    id: new Number(value.id),
                    name: value.name
                }
            }), null, 2)}</pre>
        </div>
    );
}
