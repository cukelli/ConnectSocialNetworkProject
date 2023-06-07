import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
import { GroupRequest } from './groupRequest';
import { Comment } from 'src/app/comment';

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

  getUserPosts() {

    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })


  let requestOptions = { headers: headers };  
return this.http.get(this.apiUrl + '/post/user', requestOptions);

  }

  getGroups() {

    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
    })

    let requestOptions = { headers: headers };  
    return this.http.get(this.apiUrl + '/group/all', requestOptions);
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

updatePost(postId: number,updatedPostData: updatedPostData) {
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

createPost(post: updatedPostData) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers }; 
  const url = `${this.apiUrl}/post/add`;
  return this.http.post<Post>(url,post, requestOptions);
}

createGroup(group: updatedGroupData) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  let requestOptions = { headers: headers }; 
  const url = `${this.apiUrl}/group/add`;
  return this.http.post<Group>(url,group, requestOptions);
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

getPostComments(postId: number) {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
   let requestOptions = { headers: headers }; 
    const url = `${this.apiUrl}/comment/post/${postId}`;
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

}
