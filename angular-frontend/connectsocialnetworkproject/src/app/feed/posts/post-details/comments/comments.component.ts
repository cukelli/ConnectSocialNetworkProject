import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Post } from 'src/app/post';
import { User } from 'src/app/user.model';
import { Comment } from 'src/app/comment';
import { RegistrationUser } from 'src/app/registration-user';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})

export class CommentsComponent implements OnInit {
  user!: RegistrationUser;
  post!: Post;
  @Input('comments') comments!: Array<Comment>;
  constructor(private http: HttpClient, private backendService: BackendServiceService,
    private router: Router) {
    this.user = JSON.parse(localStorage.getItem('user') || '{}');


  }
  ngOnInit(): void {
   this.backendService.getUser().subscribe({
       next: c => {  
           this.user = c;
       },
       error: er => {
           //console.error(er.error.message);
       }
   });

  }

   getCommentDetails(comment: Comment): void {
    const commentId = comment.id;

    this.backendService.getCommentDetails(commentId).subscribe(
    (commentDetails: Comment) => {
      comment = commentDetails;
      this.router.navigate(['/comment-details', comment]);
    },
    (error: any) => {
      this.router.navigate(['/feed']);
    }
  );
  }
  
  isCommentCreator(comment: Comment): boolean {
    return this.user && comment.user === this.user.username;
  }
}

