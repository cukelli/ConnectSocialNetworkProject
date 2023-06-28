import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Post } from 'src/app/post';
import { User } from 'src/app/user.model';
import { Comment } from 'src/app/comment';
import { RegistrationUser } from 'src/app/registration-user';
import { CreateComment } from 'src/app/commentCreate';
import { CountReactions } from 'src/app/countReactions';
import { ReactionType } from 'src/app/reactionType';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})

export class CommentsComponent implements OnInit {
  user!: RegistrationUser;
  post!: Post;
  @Input('comments') comments!: Array<Comment>;
  replyText!: string; 
  selectedComment!: Comment;
   isCommentLiked!: boolean;
   isCommentDisliked!: boolean;
   isCommentHearted!: boolean;
  reactionsCounter: CountReactions = {  hearts: 0,
    dislike: 0,
    like: 0}


  constructor(private http: HttpClient, private backendService: BackendServiceService,
     private router1: ActivatedRoute,
    private router: Router) {
    this.user = JSON.parse(localStorage.getItem('user') || '{}');
    this.router1.params.subscribe(params => {
      let obj = JSON.parse(JSON.stringify(params));
      this.post = obj;
  });
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

    this.backendService.getPostDetails(this.post['postId']).subscribe({
      next: (post: Post) => {
        this.post = post;
        //console.log(post.postId + "id posta");
      },
      error: (error: any) => {
        console.error('Error retrieving post details', error);
      }
    });


    }
  

  toggleReplies(comment: Comment) {
    this.selectedComment = comment;
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

  this.backendService.replyToComment(reply, this.post.postId, comment.id).subscribe({
    next: () => {
     // this.backendService.getPostComments(this.post.postId);
      this.replyText = ''; 
      this.refreshComments();

    },
    error: er => {
      console.error('Error creating reply', er);
    }
  });


  
}

refreshComments(): void {
  this.backendService.getPostComments(this.post.postId).subscribe({
    next: (comments: Comment[]) => {
      this.comments = comments;
      //console.log(comments);
    },
    error: (error: any) => {
      console.error('Error refreshing comments', error);
    }
  });
}

  sortCommentsDescendingByDate() {

        this.backendService.getPostComments(this.post.postId,'desc').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
         this.getCommntReactions();

          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }


    sortCommentsAscendingByDate() {

        this.backendService.getPostComments(this.post.postId,'asc').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();

          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }

     sortCommentsAscendingByLikes() {

        this.backendService.getPostComments(this.post.postId,'ascLikes').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
          //console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }


       sortCommentsDescendingByLikes() {

        this.backendService.getPostComments(this.post.postId,'descLikes').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
          //console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }

       sortCommentsAscendingByDislikes() {

        this.backendService.getPostComments(this.post.postId,'ascDislikes').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
         0// console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }


      sortCommentsDescendingByDislikes() {

        this.backendService.getPostComments(this.post.postId,'descDislikes').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
         0// console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }

    sortCommentsAscendingByHearts() {

        this.backendService.getPostComments(this.post.postId,'ascHearts').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
         0// console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }


   
    sortCommentsDescendingByHearts() {

        this.backendService.getPostComments(this.post.postId,'descHearts').subscribe({
       next: (data: Array<Comment>)=> {  
          this.comments = data;
          this.getCommntReactions();
         0// console.log(data);
          
       },
       error: er => {
            console.error(er.error.message);
       }
   });   }



     getCommntReactions(){
      for (let comment of this.comments){
      this.backendService.countReactions(comment.id).subscribe(
      (countReactions: CountReactions) => {
        comment.reactions = countReactions;
        this.comments.push(comment);
      },
      (error: any) => {
        console.error('Error counting reactions', error);
      }
    );
      }
    }

    commentReaction(type: ReactionType) {
      const reaction = { type: type };

      for (let comment of this.comments){

  this.backendService.reactToComment(comment.id,reaction).subscribe({
    next: () => {
         this.getCommntReactions();
      //console.log("reacted succesfuly")
     if (type === ReactionType.LIKE) {
        this.isCommentLiked = true;
        this.isCommentDisliked = false;
        this.isCommentHearted = false;
        console.log("here")
      } else if (type === ReactionType.DISLIKE) {
       this.isCommentLiked = false;
        this.isCommentDisliked = true;
        this.isCommentHearted = false;
          console.log("here")

      } else if (type === ReactionType.HEART) {
       this.isCommentLiked = false;
        this.isCommentDisliked = false;
        this.isCommentHearted = true;
         console.log("here")

      }

      
    },
    error: er => {
      console.error('Error creating reaction', er);
    }
  
  });
  }
      }

       public get ReactionType() {
      return ReactionType; 
}

}
