import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { CreateComment } from 'src/app/commentCreate';
import { Comment } from 'src/app/comment';
import { RegistrationUser } from 'src/app/registration-user';
import { CountReactions } from 'src/app/countReactions';

@Component({
  selector: 'app-reply',
  templateUrl: './reply.component.html',
  styleUrls: ['./reply.component.css']
})
export class ReplyComponent implements OnInit{
  showReplies: boolean = false;
  @Input('comment') comment!: Comment;
  reply!: Comment;
  @Input('postId') postId!: number;
  @Input('user') user!: RegistrationUser;
  @Input('comments') comments!: Array<Comment>;
  replyText!: string;


  constructor(private http: HttpClient, private backendService: BackendServiceService,
     private router1: ActivatedRoute,
    private router: Router) {
      this.user = JSON.parse(localStorage.getItem('user') || '{}');

  }
  ngOnInit(): void {
    setTimeout(() => {
      
      for (let reply of this.comment.replies){
        this.backendService.countReactions(reply.id).subscribe(
      (countReactions: CountReactions) => {
        reply.reactions = countReactions;
    
      },
      (error: any) => {
        console.error('Error counting reactions', error);
      }
    );
      }


         });

  }
  
 
  

  toggleReplies(reply: Comment){
    this.showReplies = true;
    this.reply = reply;
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
   
  replyToComment(comment: Comment, replyText: string): void {
  const reply: CreateComment = {
    text: replyText,
    parentComment: comment.id 

  };

  this.backendService.replyToComment(reply, this.postId, comment.id).subscribe({
    next: () => {
      //this.backendService.getPostComments(this.postId);
      this.replyText = ''; 
      this.refreshReplies();

    },
    error: er => {
      console.error('Error creating reply', er);
    }
  });

  
}
refreshReplies(): void {
  this.backendService.getPostComments(this.postId).subscribe({
    next: (comments: Comment[]) => {
      this.comments = comments;
      //console.log(comments);
    },
    error: (error: any) => {
      console.error('Error refreshing comments', error);
    }
  });
}

}
