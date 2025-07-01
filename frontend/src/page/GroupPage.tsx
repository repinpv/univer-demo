import {useEffect, useState} from "react";
import type {Group} from "../gen/group.ts";
import type {Student} from "../gen/student.ts";
import {CreateStudentRequest, DeleteStudentRequest, GetStudentsRequest} from "../gen/student.ts";
import {studentServiceClient} from "../grpc.ts";
import {Link, useParams} from "react-router-dom";
import {StudentListTable} from "../component/StudentListTable.tsx";
import type {OnStudentDelete} from "../component/StudentListTable.tsx";
import type {CreateStudentFormValues} from "../component/CreateStudentForm.tsx";
import {CreateStudentForm} from "../component/CreateStudentForm.tsx";
import {Timestamp} from "../gen/google/protobuf/timestamp.ts";

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

    function loadGroup() {
        setLoading(true); // Set loading state to true before fetching
        const request = GetStudentsRequest.create({groupId});
        studentServiceClient.getStudents(request)
            .then(response => {
                setGroup(response.response.group);
                setStudentList(response.response.student)
            })
            .catch(err => {
                console.error('Error loading group: ', err);
                setError(err);
            })
            .finally(() =>
                setLoading(false));
    }

    function createStudent({fio, joinDate}: CreateStudentFormValues): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            const joinDateTimestamp = Timestamp.fromDate(joinDate);
            const createStudentRequest = CreateStudentRequest.create({groupId, fio, joinDate: joinDateTimestamp});
            studentServiceClient.createStudent(createStudentRequest)
                .then(_ => {
                    loadGroup();
                    resolve();
                })
                .catch(err => {
                    console.error('Error while creating student: ', err);
                    setError(err);
                    reject(err);
                });
        });
    }

    const onStudentDelete: OnStudentDelete = (studentId?: bigint) => {
        if (!studentId) return;

        const request = DeleteStudentRequest.create({studentId});
        studentServiceClient.deleteStudent(request)
            .then(_ => loadGroup())
            .catch(err => {
                console.error('Error while deleting student: ', err);
                setError(err);
            });
    };

    useEffect(loadGroup, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {JSON.stringify(error, null, 2)}</div>;

    return (
        <>
            <h1>Group {group?.name}</h1>
            <StudentListTable loading={loading} studentList={studentList} onStudentDelete={onStudentDelete}/>
            <CreateStudentForm handleSubmit={createStudent}/>
            <Link to={'/'}>Back</Link>
        </>
    );
}