import {GrpcWebFetchTransport} from '@protobuf-ts/grpcweb-transport';
import type {IGroupServiceClient} from './gen/group.client.ts';
import {GroupServiceClient} from './gen/group.client.ts';
import type {IStudentServiceClient} from './gen/student.client.ts';
import {StudentServiceClient} from './gen/student.client.ts';

const envoyUrl = 'http://localhost:8080';

const transport = new GrpcWebFetchTransport({
    baseUrl: envoyUrl,
    format: "binary" //"text"
});

export const groupServiceClient: IGroupServiceClient = new GroupServiceClient(transport);
export const studentServiceClient: IStudentServiceClient = new StudentServiceClient(transport);