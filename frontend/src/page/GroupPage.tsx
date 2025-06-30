import {useEffect, useState} from "react";
import type {Group} from "../gen/group.ts";
import type {Student} from "../gen/student.ts";
import {studentServiceClient} from "../grpc.ts";
import {useParams} from "react-router-dom";
import {GetStudentsRequest} from "../gen/student.ts";

type GroupPageProps = {
    groupId: string
}

export function GroupPage() {

    const props = useParams<GroupPageProps>();
    const groupId = BigInt(props.groupId || "0");

    const [group, setGroup] = useState<Group | undefined>(undefined);
    const [studentList, setStudentList] = useState<Student[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true); // Set loading state to true before fetching
                const request = GetStudentsRequest.create({groupId});
                const response = await studentServiceClient.getStudents(request);
                setGroup(response.response.group);
                setStudentList(response.response.student); // Update data state with fetched data
            } catch (err) {
                console.error('Error loading groups: ', err);
                setError(err);
            } finally {
                setLoading(false); // Set loading state to false after fetching (success or error)
            }
        };

        fetchData(); // Call the async function
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {JSON.stringify(error, null, 2)}</div>;

    return (
        <div>
            <h1>Group Page</h1>
            <pre>{JSON.stringify(group, null, 2)}</pre>
            <pre>{JSON.stringify(studentList, null, 2)}</pre>
        </div>
    );
}