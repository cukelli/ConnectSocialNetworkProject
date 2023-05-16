import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BackendServiceService } from 'src/app/backend-service.service';
import { User } from 'src/app/user.model';


@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  posts: any[] = [];
  user: User;
  constructor(private http: HttpClient,private backendService: BackendServiceService) { 
        this.user = JSON.parse(localStorage.getItem('user') || '{}');

  }

 ngOnInit(): void {
  this.backendService.getUserPosts(this.user.username).subscribe(
    (data: any) => {
      this.posts = data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  }
  
}
