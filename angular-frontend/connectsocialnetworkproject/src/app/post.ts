import { Reaction } from "./reaction";

export interface Post {
  title: string;
  postId: number;
  content: string;
  creationDate: string;
  user: string;
  comments: Array<Comment>;
  imagePaths: Array<String> | null;
  reactions: Reaction;
}