import { Reaction } from "./reaction";

export interface Comment {
  id: number;
  text: string;
  timestamp: string;
  user: string;
  replies: Array<Comment>;
  reactions: Reaction;
}