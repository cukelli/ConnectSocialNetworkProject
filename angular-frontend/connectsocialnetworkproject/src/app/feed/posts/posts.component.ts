import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BackendServiceService } from 'src/app/backend-service.service';
import { User } from 'src/app/user.model';
import { Post } from 'src/app/post';
import { Router } from '@angular/router';
import { CountReactions } from 'src/app/countReactions';
import { PostIndex } from 'src/app/postIndex';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  posts!: Array<Post>;
  sort: string = 'desc';
  user: User;
  postsSearch: PostIndex[] = [];
  postIndexes!: Array<PostIndex>;
  content: string = '';
  pdfContent: string = ''
  title: string = '';
  post!: Post;
  page: number = 0;
  size: number = 10;
   reactionsCounter: CountReactions = {  hearts: 0,
    dislike: 0,
    like: 0}
    
  constructor(private http: HttpClient,private backendService: BackendServiceService,
    private router: Router) { 
        this.user = JSON.parse(localStorage.getItem('user') || '{}');

  //       this.backendService.getUserPostElastic().subscribe({
  //      next: (data: Array<PostIndex>)=> {  
  //         this.postIndexes = data;
  //         this.getPostReactions();
  //      },
  //      error: er => {
  //         //  console.error(er.error.message);
  //      }
  //  }); 
  this.getAllUserPostElastic();

  }

 ngOnInit(): void {
  }
  getPostDetails(postI: PostIndex): void {

    const postId = postI.databaseId == null ? 0: postI.databaseId;

    this.backendService.getPostDetails(postId).subscribe(
      (postDetails: Post) => {
        let post = postDetails;
        this.router.navigate(['/post-details',post])
        console.log(this.user + "user");

       // console.log(groupDetails);
      }, (error: any) => {
        this.router.navigate(['/feed']);
      }
    
    );
  }



    
  searchPostsByContent(): void {
    this.backendService.searchPostsByContent(this.content, this.pdfContent).subscribe(
      (data: PostIndex[]) => {
        this.postsSearch = data;
        this.postIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
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


   searchPostsByTitle(): void {
    this.backendService.searchPostsByTitle(this.title).subscribe(
      (data: PostIndex[]) => {
        this.postsSearch = data;
        this.postIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
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
  

 getPostReactions() {
  for (let post of this.posts) {
    this.backendService.countReactionsPost(post.postId).subscribe(
      (countReactions: CountReactions) => {
        post.reactions = countReactions;
        //console.log(post.reactions);
      },
      (error: any) => {
        console.error('Error counting reactions', error);
      }
    );
  }
}


getAllUserPostElastic() {
  this.backendService.getUserPostElastic().subscribe({
    next: (data: any)=> {  
       this.postIndexes = data;
       
    },
    error: er => {
       //  console.error(er.error.message);
    }
});  }


}
