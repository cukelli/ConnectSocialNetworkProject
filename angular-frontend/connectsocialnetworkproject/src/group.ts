import { Post } from "./app/post";

export interface Group {
    groupId: number;
    name: string;
    description: string;
    createdAt: Date;
    posts: Array<Post>;
    admins: Array<any>;


}