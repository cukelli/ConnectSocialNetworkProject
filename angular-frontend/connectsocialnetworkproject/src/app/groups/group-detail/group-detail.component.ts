import { Component, Input, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { HeaderComponent } from 'src/app/home-page/header/header.component';
import { Group } from 'src/group';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/post';

@Component({
  selector: 'app-group-detail',
  templateUrl: './group-detail.component.html',
  styleUrls: ['./group-detail.component.css']
})
export class GroupDetailComponent implements OnInit {
    isAdmin: boolean = false;
    group!: Group;
    admins!: Array<any>;
    isGroupUpdated: boolean = false;
    posts: Array<Post> = [];



  constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
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

    this.backendService.getPostsInGroup(this.group['groupId']).subscribe({
      next: (c: Array<Post>) => {
        this.posts = c;
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

    getPostDetails(post: Post): void {
    const postId = post.postId;

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

}
