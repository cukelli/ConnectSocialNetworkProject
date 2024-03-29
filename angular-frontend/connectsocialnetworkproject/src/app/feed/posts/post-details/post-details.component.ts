  import { Component, OnInit, Input } from '@angular/core';
  import { ActivatedRoute, Router } from '@angular/router';
  import { BackendServiceService } from 'src/app/backend-service.service';
  import { Post } from 'src/app/post';
  import { Comment } from 'src/app/comment';
import { CreateComment } from 'src/app/commentCreate';
import { CountReactions } from 'src/app/countReactions';
import { ReactionType } from 'src/app/reactionType';

  @Component({
    selector: 'app-post-details',
    templateUrl: './post-details.component.html',
    styleUrls: ['./post-details.component.css']
  })
  export class PostDetailsComponent implements OnInit {
    post!: Post;
    isPostUpdated: boolean = false;
    reactionype!: ReactionType;
    commentsInit!: Array<Comment>;
    comments: Array<Comment> = [];
    errorMessage: string | null = null;
    errorMessage2: string | null = null;
    isPostLiked!: boolean;
    isPostDisliked!: boolean;
    isPostHearted!: boolean;


    //kreiranje komentara
     text!: string;
     isCommentCreated: boolean = false;

    constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
      private routing: Router) {
      this.router.params.subscribe(params => {
      let obj = JSON.parse(JSON.stringify(params));
      this.post = obj;
      this.getPostComments();
    });  
    }

    ngOnInit(): void {

      

    }
  deletePost(): void {
        this.backendService.deletePost(this.post['postId']).subscribe({
        next: () => {
        this.routing.navigate(['/post-deleted'],{ queryParams: { deleteSuccess: true } }).then(() => {
            setTimeout(() => {
              this.routing.navigate(['feed']);

            },2000)
          });
        },
       error: (er: any) => {
      this.errorMessage = 'You cannot delete this post since you are not the creator.';
      setTimeout(() => {
        this.errorMessage = null;
      }, 4000);
        }
    });
    }
      updatePost(): void {
        const updatedPostData = {
        content: this.post.content,
    };
        this.backendService.updatePost(this.post['postId'],updatedPostData).subscribe({
        next: (updatedPost: Post) => {
        this.isPostUpdated = true; 
          setTimeout(() => {
        this.isPostUpdated = false;
      }, 5000);
        return;
      
        },
        error: er => {
          this.errorMessage2 = 'You cannot update this post since you are not creator.';
          setTimeout(() => {
            this.errorMessage2 = null;
        }, 4000);
        }
    });
    }

    getCommntReactions(){
      for (let comment of this.commentsInit){
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
    
    getPostComments(): void {
    this.backendService.getPostComments(this.post['postId']).subscribe({
      next: (c: Array<Comment>) => {
        this.commentsInit = c;
        this.getCommntReactions();
      },
      error: er => {
        console.error(er);
      }
    });
    }

      createComment(): void {
    const commentForCreation: CreateComment = {
      text: this.text,
    };
      this.backendService.createComment(commentForCreation,this.post['postId']).subscribe({
       next: () => {
         this.isCommentCreated = true;
        setTimeout(() => {
          this.isCommentCreated = false;
          this.comments = [];
          this.getPostComments();
        }, 5000);
        return;  
       },
       error: er => {
          //console.error('Error creating post', er);

       }
   });
  }

  postReaction(type: ReactionType) {
  const reaction = { type: type };


  this.backendService.reactToPost(this.post['postId'],reaction).subscribe({
    next: () => {
      //console.log("reacted succesfuly")
     if (type === ReactionType.LIKE) {
        this.isPostLiked = true;
        this.isPostDisliked = false;
        this.isPostHearted = false;
      } else if (type === ReactionType.DISLIKE) {
        this.isPostLiked = false;
        this.isPostDisliked = true;
        this.isPostHearted = false;
      } else if (type === ReactionType.HEART) {
        this.isPostLiked = false;
        this.isPostDisliked = false;
        this.isPostHearted = true;
      }

      
    },
    error: er => {
      console.error('Error creating reaction', er);
    }
  });
  }

  public get ReactionType() {
  return ReactionType; 
}
}

  
