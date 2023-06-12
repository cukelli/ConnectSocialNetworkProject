  import { Component, OnInit, Input } from '@angular/core';
  import { ActivatedRoute, Router } from '@angular/router';
  import { BackendServiceService } from 'src/app/backend-service.service';
  import { Post } from 'src/app/post';
  import { Comment } from 'src/app/comment';
import { CreateComment } from 'src/app/commentCreate';


  @Component({
    selector: 'app-post-details',
    templateUrl: './post-details.component.html',
    styleUrls: ['./post-details.component.css']
  })
  export class PostDetailsComponent implements OnInit {
    post!: Post;
    isPostUpdated: boolean = false;
    comments!: Array<Comment>;
    errorMessage: string | null = null;
    errorMessage2: string | null = null;

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
    
    getPostComments(): void {
    this.backendService.getPostComments(this.post['postId']).subscribe({
      next: (c: Array<Comment>) => {
        this.comments = c;
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
          this.getPostComments();
        }, 5000);
        return;  
       },
       error: er => {
          //console.error('Error creating post', er);


       }
   });
  }


  }
