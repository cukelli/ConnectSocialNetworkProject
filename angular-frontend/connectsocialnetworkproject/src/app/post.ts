import { Reaction } from "./reaction";

export interface Post {
  postId: number;
  content: string;
  creationDate: string;
  user: string;
  comments: Array<Comment>;
  imagePaths: Array<String> | null;
   reactions: Reaction;
}