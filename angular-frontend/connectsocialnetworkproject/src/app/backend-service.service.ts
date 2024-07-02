import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from './user.model';
import { RegistrationUser } from './registration-user';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { ChangePassword } from './change-password';
import jwt_decode from "jwt-decode";
import { Group } from 'src/group';
import { Post } from './post';
import { updatedGroupData } from './updatedGroupData';
import { updatedPostData } from './updatedPostData';
import { updatedCommentData } from './updatedCommentData';
import { GroupRequest } from './groupRequest';
import { Comment } from 'src/app/comment';
import { CreateComment } from './commentCreate';
import { UpdatePostContent } from 'updatePostContent';
import { CountReactions } from './countReactions';
import { Reaction } from 'src/reaction';
import { ReactionType } from './reactionType';
import { UserUpdate } from './userUpdate';
import { FriendRequest } from './friendRequest';
import { SendFriendRequest } from './sendFriendRequest';
import { GroupIndex } from './GroupIndex';
import { PostIndex } from './postIndex';

@Injectable({
  providedIn: 'root'
})

export class BackendServiceService {
  private apiUrl = 'http://localhost:8080';
  public userDetails!: RegistrationUser; 


  constructor(private http: HttpClient) { }

   login(user: User) {
    return this.http.post(this.apiUrl + '/user/login', user);
  }

  getDecodedToken(token: string) {
  return jwt_decode(token) as { username: string };
}

  registration(user: RegistrationUser) {
  return this.http.post(this.apiUrl + '/user/registration', user);
  }

getUserPosts(sortOrder?: string) {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let sortOrdertemp = "asc"
  if (sortOrder !== undefined) {
     sortOrdertemp = sortOrder;
  }

  return this.http.get<Array<Post>>(this.apiUrl + `/post/user?sort=${sortOrdertemp}`, { headers });
}

  getGroups() {

    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    }) 

    let requestOptions = { headers: headers };  
    return this.http.get(this.apiUrl + '/group/all', requestOptions);
  }


  getGroupsElastic() {

    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    }) 

    let requestOptions = { headers: headers };  
    return this.http.get(this.apiUrl + '/group/all/elastic', requestOptions);
  }


  getUser(): Observable<RegistrationUser> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })

  let requestOptions = { headers: headers };
  return this.http.get<RegistrationUser>(this.apiUrl + '/user/', requestOptions);
}

  changePasswordUser(changePassword: ChangePassword): Observable<JSON> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })

  let requestOptions = { headers: headers };
  return this.http.post<JSON>(this.apiUrl + '/user/changePassword',changePassword,requestOptions);
}

getGroupDetails(groupId: number): Observable<Group> {

  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    })

     let requestOptions = { headers: headers }; 
     const url = `${this.apiUrl}/group/${groupId}`;
     return this.http.get<Group>(url, requestOptions);

}

deleteGroup(groupId: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
    let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/group/delete/${groupId}`;
     return this.http.delete(url, requestOptions);

}

deletePost(postId: number) {

  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
    let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/post/delete/${postId}`;
     return this.http.delete(url, requestOptions);

}

checkMembership(groupId: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
      let requestOptions = { headers: headers };

   const url = `${this.apiUrl}/group/isAdmin/${groupId}`;
     return this.http.get(url, requestOptions);

}

getPostDetails(postId: number): Observable<Post> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    })

     let requestOptions = { headers: headers }; 
     const url = `${this.apiUrl}/post/${postId}`;
     return this.http.get<Post>(url, requestOptions);

}

updatePost(postId: number,updatedPostData: UpdatePostContent) {
    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
    let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/post/update/${postId}`;
     return this.http.put<Post>(url,updatedPostData, requestOptions);

}
updateGroup(groupId: number, updatedGroupData: updatedGroupData) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers }; 
  const url = `${this.apiUrl}/group/update/${groupId}`;
  return this.http.put<Group>(url, updatedGroupData, requestOptions);
}



joinGroup(groupRequest: GroupRequest) {
    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
    let requestOptions = { headers: headers }; 
      const url = `${this.apiUrl}/groupRequest/add`;
      return this.http.post<GroupRequest>(url,groupRequest, requestOptions);

}

getGroupRequest(groupId: number): Observable<GroupRequest> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/groupRequest/${groupId}`;
  return this.http.get<GroupRequest>(url, requestOptions);
}

getPostsInGroup(groupId: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
   let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/group/posts/${groupId}`;
  return this.http.get<Array<Post>>(url, requestOptions);
}

getPostComments(postId: number,sortOrder?: string) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

    let sortOrdertemp = "asc"
  if (sortOrder !== undefined) {
     sortOrdertemp = sortOrder;
  }
   let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/comment/post/${postId}?sort=${sortOrdertemp}`;
  return this.http.get<Array<Comment>>(url, requestOptions);
}

getCommentDetails(commentId: number): Observable<Comment>{
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
   let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/comment/${commentId}`;
  return this.http.get<Comment>(url, requestOptions);
}

createComment(comment: CreateComment,postId: number): Observable<Comment> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers }; 
  const url = `${this.apiUrl}/comment/add/${postId}`;
  return this.http.post<Comment>(url,comment, requestOptions);
}


updateComment(commentId: number, updatedCommentData: updatedCommentData) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers }; 
  const url = `${this.apiUrl}/comment/update/${commentId}`;
  return this.http.put<Comment>(url, updatedCommentData, requestOptions);
}

deleteComment(id: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
    let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/comment/delete/${id}`;
     return this.http.delete(url, requestOptions);

}

replyToComment(comment: CreateComment,postId: number,id: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/comment/reply/${postId}/${id}`;
  return this.http.post<Comment>(url,comment, requestOptions);
}

countReactions(commentId: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/comment/reactions/${commentId}`;
  return this.http.get<CountReactions>(url, requestOptions);
}

countReactionsPost(postId: number) {
   let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers };
    const url = `${this.apiUrl}/post/reactions/${postId}`;
  return this.http.get<CountReactions>(url, requestOptions);
}

reactToPost(postId: number, reaction: { type: ReactionType }) {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/reaction/add/${postId}`;
  
  return this.http.post<Reaction>(url, reaction, requestOptions);
}

reactToComment(id: number, reaction: { type: ReactionType }) {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/reaction/add/comment/${id}`;
  
  return this.http.post<Reaction>(url, reaction, requestOptions);
}

updateUser(updatedUserData: UserUpdate) {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/user/update`;
  
  return this.http.put<UserUpdate>(url,updatedUserData,requestOptions);
}

getUserGroups() {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/user/getUserGroups`;
  
  return this.http.get<Array<Group>>(url,requestOptions);
}


getUserFriends() {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/friendRequest/user/friends`;
  
  return this.http.get<Array<RegistrationUser>>(url,requestOptions);
}

getUsersExceptMe() {

   const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/user/all/allUsersNotMe`;
  
  return this.http.get<Array<RegistrationUser>>(url,requestOptions);

}


getUserFriendRequests() {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/friendRequest/user`;
  
  return this.http.get<Array<FriendRequest>>(url,requestOptions);
}

AnswerFriendRequest(friendRequestId: number, approved: boolean) {
 const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/friendRequest/answer/${friendRequestId}`;
  
  return this.http.post<FriendRequest>(url,{approved: approved},requestOptions);
}

sendFriendRequest(sendFriendRequest: SendFriendRequest) {

   const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  const requestOptions = { headers: headers };
  const url = `${this.apiUrl}/friendRequest/add`;
  
  return this.http.post<FriendRequest>(url,sendFriendRequest,requestOptions);

}

//TODO APIS FOR ELASTIC SEARCH

searchGroupsByNameBackend(name: string): Observable<GroupIndex[]> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams()
    .set('name', name)
  return this.http.get<GroupIndex[]>(`${this.apiUrl}/group/searchByName`, { params, headers });
}

searchGroupsByDescriptionBackend(description: string, pdfDescription: string): Observable<GroupIndex[]> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams()
    .set('description', description)
    .set('pdfDescription', pdfDescription)
  return this.http.get<GroupIndex[]>(`${this.apiUrl}/group/searchByDescriptionOrPdfDescription`, { params, headers });
}


createGroup(group: updatedGroupData, groupDescriptionPdf: File | null): Observable<Group> {
  const formData: FormData = new FormData();
  
  formData.append('groupRequest', new Blob([JSON.stringify(group)], {
    type: 'application/json'
  }));

  if (groupDescriptionPdf) {
    formData.append('groupDescriptionPdf', groupDescriptionPdf);
  }

  let headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  const url = `${this.apiUrl}/group/add`;
  return this.http.post<Group>(url, formData, { headers });
}


createPost(post: updatedPostData, postContentPdf: File | null): Observable<Post> {
  const formData: FormData = new FormData();
  
  formData.append('postRequest', new Blob([JSON.stringify(post)], {
    type: 'application/json'
  }));

  if (postContentPdf) {
    formData.append('postContentPdf', postContentPdf);
  }

  let headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  const url = `${this.apiUrl}/post/add`;
  return this.http.post<Post>(url, formData, { headers });
}


createPostInGroup(post: updatedPostData, postContentPdf: File | null, groupId: number): Observable<Post> {
  const formData: FormData = new FormData();
  
  formData.append('postRequest', new Blob([JSON.stringify(post)], {
    type: 'application/json'
  }));

  if (postContentPdf) {
    formData.append('postContentPdf', postContentPdf);
  }

  let headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  const url = `${this.apiUrl}/group/${groupId}/post`;
  return this.http.post<Post>(url, formData, { headers });
}





searchPostsByTitle(title: string, groupId: number = 0): Observable<PostIndex[]> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams()
    .set('title', title)
  
  if (groupId !== 0){
    return this.http.get<PostIndex[]>(`${this.apiUrl}/post/searchByTitle/${groupId}`, { params, headers });

  } else {
  
  return this.http.get<PostIndex[]>(`${this.apiUrl}/post/searchByTitle`, { params, headers });
  }
}


searchPostsByContent(content: string, pdfContent: string): Observable<PostIndex[]> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams()
    .set('content', content)
    .set('pdfContent', pdfContent)
  return this.http.get<PostIndex[]>(`${this.apiUrl}/post/searchByContentOrPdfContent`, { params, headers });
}


searchPostsInGroupByContent(content: string, pdfContent: string, groupId: number): Observable<PostIndex[]> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams()
    .set('content', content)
    .set('pdfContent', pdfContent)
  return this.http.get<PostIndex[]>(`${this.apiUrl}/post/searchByContentOrPdfContent/${groupId}`, { params, headers });
}

getPostsElastic() {

  let headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${localStorage.getItem('token')}`
  }) 

  let requestOptions = { headers: headers };  
  return this.http.get(this.apiUrl + '/post/all/elastic', requestOptions);
}


getUserPostElastic() {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  return this.http.get<Array<PostIndex>>(this.apiUrl + `/post/user/elastic`, { headers });
}

getGroupPostsELastic(groupId: number) {
  console.log(this.apiUrl + `group/all/elastic/${groupId}`)
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  return this.http.get<Array<PostIndex>>(this.apiUrl + `/group/all/elastic/${groupId}`, { headers });
}


getGroupsByPostRange(minPosts?: number, maxPosts?: number): Observable<Array<GroupIndex>> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams();
  if (minPosts !== null && minPosts !== undefined) {
    params = params.append('minPosts', minPosts.toString());
  }
  if (maxPosts !== null && maxPosts !== undefined) {
    params = params.append('maxPosts', maxPosts.toString());
  }

  return this.http.get<Array<GroupIndex>>(`${this.apiUrl}/group/search/byPostRange`, { headers, params });
}



getPostsByLikeRange(minLikes?: number, maxLikes?: number, groupId? : number): Observable<Array<PostIndex>> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  let params = new HttpParams();
  if (minLikes !== null && minLikes !== undefined) {
    params = params.append('minLikes', minLikes.toString());
  }
  if (maxLikes !== null && maxLikes !== undefined) {
    params = params.append('maxLikes', maxLikes.toString());
  }

  return this.http.get<Array<PostIndex>>(`${this.apiUrl}/post/search/byLikeRange`, { headers, params });
}



}
