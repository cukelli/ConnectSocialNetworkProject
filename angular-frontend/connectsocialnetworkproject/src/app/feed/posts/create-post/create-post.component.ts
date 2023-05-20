import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Post } from 'src/app/post';
import { updatedPostData } from 'src/app/updatedPostData';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
    postContent!: string;


    constructor(private backendService: BackendServiceService) { }

  ngOnInit(): void {
  }

   createPost(): void {
    const postForCreation: updatedPostData = {
      content: this.postContent 
    };
      
      this.backendService.createPost(postForCreation).subscribe({
       next: () => {
        //console.log('Post created successfully');
     
       },
       error: er => {
          //console.error('Error creating post', er);


       }
   });
  }

}
