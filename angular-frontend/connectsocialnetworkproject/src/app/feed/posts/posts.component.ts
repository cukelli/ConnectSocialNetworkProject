import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BackendServiceService } from 'src/app/backend-service.service';
import { User } from 'src/app/user.model';
import { Post } from 'src/app/post';
import { Router } from '@angular/router';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  posts!: Array<Post>;
  sort: string = 'desc';
  user: User;
  constructor(private http: HttpClient,private backendService: BackendServiceService,
    private router: Router) { 
        this.user = JSON.parse(localStorage.getItem('user') || '{}');

        this.backendService.getUserPosts('asc').subscribe({
       next: (data: Array<Post>)=> {  
          this.posts = data;
          
       },
       error: er => {
          //  console.error(er.error.message);
       }
   }); 

  }

 ngOnInit(): void {
  }
   getPostDetails(post: Post): void {
    const postId = post.postId;

    this.backendService.getPostDetails(postId).subscribe(
      (postDetails: Post) => {
        post = postDetails;
        this.router.navigate(['/post-details',post])
        console.log(this.user + "user");

       // console.log(groupDetails);
      }, (error: any) => {
        this.router.navigate(['/feed']);
      }
    
    );
  }

  sortPostsAscendingByDate() {
        this.backendService.getUserPosts('asc').subscribe({
       next: (data: Array<Post>)=> {  
          this.posts = data;
       },
       error: er => {
            console.error(er.error.message);
       }
   });  
   }

  

  sortPostsDescendingByDate() {

        this.backendService.getUserPosts('desc').subscribe({
       next: (data: Array<Post>)=> {  
          this.posts = data;
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }
  
}
