import { RegistrationUser } from "./registration-user";

export interface FriendRequest {

    id: number;
    sentBy: RegistrationUser;
    userSentByInfo: String;
    approved: true;
}