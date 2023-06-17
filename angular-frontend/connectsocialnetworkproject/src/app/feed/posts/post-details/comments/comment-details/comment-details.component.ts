import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Comment } from 'src/app/comment';
import { Location } from '@angular/common';


@Component({
  selector: 'app-comment-details',
  templateUrl: './comment-details.component.html',
  styleUrls: ['./comment-details.component.css']
})
export class CommentDetailsComponent implements OnInit {
    comment!: Comment;
    isCommentUpdated: boolean = false;
    isCommentDeleted: boolean = false;
    errorMessage: string | null = null;
    errorMessage2: string | null = null;

     constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
      private routing: Router, private location: Location) {
      this.router.params.subscribe(params => {
      let obj = JSON.parse(JSON.stringify(params));
      this.comment = obj;
    });  
    }

  ngOnInit(): void {
  }

   updateComment(): void {
        const updatedCommentData = {
        text: this.comment.text,
    };
        this.backendService.updateComment(this.comment['id'],updatedCommentData).subscribe({
        next: (updatedComment: Comment) => {
        this.isCommentUpdated = true; 
          setTimeout(() => {
        this.isCommentUpdated = false;
      }, 5000);
        return;
      
        }
    });
    }

 deleteComment(): void {
        this.backendService.deleteComment(this.comment['id']).subscribe({
        next: () => {
             this.isCommentDeleted = true; 
          setTimeout(() => {
        this.isCommentDeleted = false;
          this.location.back();
      }, 5000);
        return;
    }
          });

    }

}
