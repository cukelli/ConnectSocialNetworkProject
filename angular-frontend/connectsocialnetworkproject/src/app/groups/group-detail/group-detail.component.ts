import { Component, Input, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { HeaderComponent } from 'src/app/home-page/header/header.component';
import { Group } from 'src/group';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/post';
import { NgxImageCompressService } from 'ngx-image-compress';
import { updatedPostData } from 'src/app/updatedPostData';
import { PostIndex } from 'src/app/postIndex';

@Component({
  selector: 'app-group-detail',
  templateUrl: './group-detail.component.html',
  styleUrls: ['./group-detail.component.css']
})
export class GroupDetailComponent implements OnInit {
    isAdmin: boolean = false;
    group!: Group;
    postContent!: string;
    postTitle!: string;
    isPostCreated: boolean = false;
    minValue: number = 0;
    maxValue: number = 0;
    content: string = '';
    selectedImage!: File;
    title: string = '';
    postContentPdf!: File;
    pdfContent: string = ''
    postsSearch: PostIndex[] = [];
    postIndexes!: Array<PostIndex>;
    imageToShow!: String;
    admins!: Array<any>;
    isGroupUpdated: boolean = false;
    posts: Array<Post> = [];



  constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
    private imageCompressService: NgxImageCompressService,
    private routing: Router) {
  this.router.params.subscribe(params => {
    let obj = JSON.parse(JSON.stringify(params));
    this.group = obj;
    this.admins = JSON.parse(obj.admins)
   // console.log(this.admins)
    //console.log(obj);
  });  
  }
  ngOnInit(): void {
    this.backendService.checkMembership(this.group['groupId']).subscribe({
      next: (c: any) => {
        this.isAdmin = c;
      },
      error: er => {
        console.error;
      }
    });

    this.backendService.getGroupPostsELastic(this.group['groupId']).subscribe({
      next: (c: Array<PostIndex>) => {
        this.postIndexes = c;
        
      },
      error: er => {
        console.error(er);
      }
    });
  }

  deleteGroup(): void {
      this.backendService.deleteGroup(this.group['groupId']).subscribe({
       next: () => {
      this.routing.navigate(['/success-deletion-group'],{ queryParams: { deleteSuccess: true } }).then(() => {
          setTimeout(() => {
            this.routing.navigate(['feed']);
          },2000)
        });
       },
       error: er => {
      //console.error('Error deleting group:', er);

       }
   });
  }

    updateGroup(): void {
      const updatedGroupData = {
      name: this.group.name,
      description: this.group.description
  };
      this.backendService.updateGroup(this.group['groupId'],updatedGroupData).subscribe({
       next: (updatedGroup: Group) => {
       this.isGroupUpdated = true; 
        setTimeout(() => {
      this.isGroupUpdated = false;
    }, 5000);     
       },
       error: er => {

       }
   });
  }


  createPost(): void {
    if (this.selectedImage) {
    const reader = new FileReader();
    reader.onload = () => {
      const imageBase64 = reader.result as string;
      this.imageCompressService.compressFile(imageBase64, -1, 7, 17).then((compressedImage: string) => {
        const postForCreation: updatedPostData = {
          title: this.postTitle,
          content: this.postContent,
          image: compressedImage
        };
        this.imageToShow = compressedImage;
        this.createPostWithImage(postForCreation);
        this.getAllPostsGroup();

      });
    };
    reader.readAsDataURL(this.selectedImage);
  } else {
    const postForCreation: updatedPostData = {
      title: this.postTitle,
      content: this.postContent,
      image: ""
    };
    this.createPostWithImage(postForCreation);
    this.getAllPostsGroup();
  
  }
    }
    
  

    createPostWithImage(postForCreation: updatedPostData): void {
  this.backendService.createPostInGroup(postForCreation, this.postContentPdf,this.group.groupId).subscribe({
    next: () => {
      this.isPostCreated = true;
      this.backendService.getPostsInGroup(this.group['groupId']).subscribe({
        next: (c: Array<Post>) => {
          this.posts = c;
        },
        error: er => {
          console.error(er);
        }
      });
      setTimeout(() => {
        this.isPostCreated = false;
      }, 5000);
    },
    error: er => {
      // Handle the error
    }
  });
}



    getPostDetails(post: PostIndex): void {
      const postId = post.databaseId == null ? 0: post.databaseId;

    this.backendService.getPostDetails(postId).subscribe(
      (postDetails: Post) => {
        post = postDetails;
        this.routing.navigate(['/post-details',post])
       // console.log(groupDetails);
      }, (error: any) => {
        this.routing.navigate(['/feed']);
      }
    
    );
  }


  searchPostsByTitle(): void {
    this.backendService.searchPostsByTitle(this.title, this.group['groupId']).subscribe(
      (data: PostIndex[]) => {
        this.postsSearch = data;
        this.postIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }
  

  onImageSelected(event: any) {
    this.selectedImage = event.target.files[0];
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.postContentPdf = input.files[0];
    }
  }

  searchPostsByContent(): void {
    console.log(this.content)
    this.backendService.searchPostsInGroupByContent(this.content, this.pdfContent,this.group['groupId']).subscribe(
      (data: PostIndex[]) => {
        this.postsSearch = data;
        this.postIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }


  getAllPostsGroup() {
    this.backendService.getGroupPostsELastic(this.group.groupId).subscribe({
      next: (data: any)=> {  
        console.log(data);
        console.log("data here");
         this.postIndexes = data;
         
      },
      error: er => {
         //  console.error(er.error.message);
      }
  });  }
  

  searchPostsByLikeRange(): void {
    this.backendService.getPostsByLikeRange(this.minValue, this.maxValue, this.group.groupId).subscribe(
      (data: PostIndex[]) => {
        this.postsSearch = data;
        this.postIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }



}
