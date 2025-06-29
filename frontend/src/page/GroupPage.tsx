import { useParams } from 'react-router-dom';
import {useQuery} from "@connectrpc/connect-query";
import {getStudents} from "../gen/com/demo/univer/grpc/student/v1/student-StudentService_connectquery.ts";

export function GroupPage() {
    const { groupId } = useParams<{ groupId: string }>();

    const {data, isLoading, error} = useQuery(
        getStudents,
        {
            groupId: BigInt(groupId || "0")
        });

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error?.code}, {error?.message}</div>;

    return (
        <div>
            <h1>Group Page</h1>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
    );
}