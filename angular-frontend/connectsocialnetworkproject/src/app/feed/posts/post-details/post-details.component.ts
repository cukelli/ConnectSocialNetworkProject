import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { Post } from 'src/app/post';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post!: Post;

  constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
    private routing: Router) {
    this.router.params.subscribe(params => {
    let obj = JSON.parse(JSON.stringify(params));
    this.post = obj;
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
       error: er => {
      //console.error('Error deleting group:', er);

       }
   });
  }

}
