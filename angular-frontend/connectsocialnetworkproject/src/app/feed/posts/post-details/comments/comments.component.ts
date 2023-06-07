import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Post } from 'src/app/post';
import { User } from 'src/app/user.model';
import { Comment } from 'src/app/comment';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})

export class CommentsComponent implements OnInit {
      user!: User;
      post!: Post;
      @Input('comments') comments!: Array<Comment>;
       constructor(private http: HttpClient,private backendService: BackendServiceService,
    private router: Router) { }
  ngOnInit(): void {
      this.user = JSON.parse(localStorage.getItem('user') || '{}');
              console.log(this.comments);


  //       this.backendService.getPostComments(this.post.postId).subscribe({
  //      next: (data: Array<Comment>)=> {  
  //         this.comments = data;
          
  //      },
  //      error: er => {
  //         //  console.error(er.error.message);
  //      }
  //  }); 

  }
    
}
