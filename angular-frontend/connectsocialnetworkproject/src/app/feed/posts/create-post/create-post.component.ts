  import { Component, OnInit } from '@angular/core';
  import { BackendServiceService } from 'src/app/backend-service.service';
  import { Post } from 'src/app/post';
  import { updatedPostData } from 'src/app/updatedPostData';
  import { DomSanitizer } from '@angular/platform-browser';
  import { NgxImageCompressService } from 'ngx-image-compress';

  @Component({
    selector: 'app-create-post',
    templateUrl: './create-post.component.html',
    styleUrls: ['./create-post.component.css']
  })
  export class CreatePostComponent implements OnInit {
      postContent!: string;
      isPostCreated: boolean = false;
      selectedImage!: File;
      imageToShow!: String;

      constructor(private backendService: BackendServiceService, private imageCompressService: NgxImageCompressService,
        private domSanitizer: DomSanitizer) { }

    ngOnInit(): void {
    }

    createPost(): void {
    if (this.selectedImage) {
    const reader = new FileReader();
    reader.onload = () => {
      const imageBase64 = reader.result as string;
      this.imageCompressService.compressFile(imageBase64, -1, 7, 17).then((compressedImage: string) => {
        const postForCreation: updatedPostData = {
          content: this.postContent,
          image: compressedImage
        };
        this.imageToShow = compressedImage;
        this.createPostWithImage(postForCreation);
      });
    };
    reader.readAsDataURL(this.selectedImage);
  } else {
    const postForCreation: updatedPostData = {
      content: this.postContent,
      image: ""
    };
    this.createPostWithImage(postForCreation);
    console.log("succesfuly posted without image")
  }

    }


    createPostWithImage(postForCreation: updatedPostData): void {
  this.backendService.createPost(postForCreation).subscribe({
    next: () => {
      this.isPostCreated = true;
      setTimeout(() => {
        this.isPostCreated = false;
      }, 5000);
    },
    error: er => {
      // Handle the error
    }
  });
}



      onImageSelected(event: any) {
      this.selectedImage = event.target.files[0];
    }

  }

