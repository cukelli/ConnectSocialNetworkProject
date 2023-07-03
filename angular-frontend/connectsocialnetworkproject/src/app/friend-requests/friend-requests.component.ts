import { Component, OnInit } from '@angular/core';
import { RegistrationUser } from '../registration-user';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgxImageCompressService } from 'ngx-image-compress';
import { BackendServiceService } from '../backend-service.service';
import { FriendRequest } from '../friendRequest';

@Component({
  selector: 'app-friend-requests',
  templateUrl: './friend-requests.component.html',
  styleUrls: ['./friend-requests.component.css']
})
export class FriendRequestsComponent implements OnInit {

     user!: RegistrationUser;
     friends!: Array<RegistrationUser>;
     requests!: Array<FriendRequest>;

      constructor(private http: HttpClient,private backendService: BackendServiceService,
     private imageCompressService: NgxImageCompressService,private router: Router,
        private domSanitizer: DomSanitizer) {
    }
  
  ngOnInit(): void {

      this.backendService.getUser().subscribe({
       next: c => {  
           this.user = c;
       },
       error: er => {
           //console.error(er.error.message);
       }
  })

      this.backendService.getUserFriendRequests().subscribe({
       next: c => {  
           this.requests = c;
           console.log(c);
          
       },
       error: er => {
           //console.error(er.error.message);
       }
  })

}

  acceptFriendRequest(friendRequest: FriendRequest): void {
    const friendRequestId = friendRequest.id;
    console.log(friendRequestId);

    this.backendService.AnswerFriendRequest(friendRequestId, true).subscribe(
      () => {
          this.backendService.getUserFriendRequests().subscribe({
           next: c => {  
           this.requests = c;
           console.log(c);
          
            },
            error: er => {
                //console.error(er.error.message);
            }
        })

      }, (error: any) => {
        this.router.navigate(['/friend-requests']);
        console.log("here")
      }
    
    );
  }


  rejectFriendRequest(friendRequest: FriendRequest): void {
    const friendRequestId = friendRequest.id;
    console.log(friendRequestId);

    this.backendService.AnswerFriendRequest(friendRequestId, false).subscribe(
      () => {
          this.backendService.getUserFriendRequests().subscribe({
           next: c => {  
           this.requests = c;
           console.log(c);
          
            },
            error: er => {
                //console.error(er.error.message);
            }
        })

      }, (error: any) => {
        this.router.navigate(['/friend-requests']);
        console.log("here")
      }
    
    );
  }


}
