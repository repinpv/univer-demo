//import {Link} from 'react-router-dom';
//import React from "react";

import {useEffect, useState} from "react";
import type {ExtGroup} from "../gen/group.ts";
import {CreateGroupRequest, GetGroupsRequest} from "../gen/group.ts";
import {groupServiceClient} from "../grpc.ts";
import {GroupListTable} from "../component/GroupListTable.tsx";
import type {CreateGroupFormValues} from "../component/CreateGroupForm.tsx";
import {CreateGroupForm} from "../component/CreateGroupForm.tsx";

export function GroupListPage() {
    const [groupList, setGroupList] = useState<ExtGroup[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    function loadGroupList() {
        setLoading(true); // Set loading state to true before fetching

        const request = GetGroupsRequest.create();
        groupServiceClient.getGroups(request)
            .then(response =>
                setGroupList(response.response.group))
            .catch(err => {
                console.error('Error loading groups: ', err);
                setError(err);
            })
            .finally(() =>
                setLoading(false))
    }

    function createGroup({name}: CreateGroupFormValues): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            const createGroupRequest = CreateGroupRequest.create({name});
            groupServiceClient.createGroup(createGroupRequest)
                .then(_ => {
                    loadGroupList();
                    resolve();
                })
                .catch(err => {
                    console.error(err);
                    setError(err);
                    reject(err);
                });
        });
    }

    useEffect(loadGroupList, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {JSON.stringify(error, null, 2)}</div>;

    return (
        <div>
            <h1>Group list</h1>
            <GroupListTable loading={loading} groupList={groupList}/>
            <CreateGroupForm handleSubmit={createGroup}/>
        </div>
    );
}
