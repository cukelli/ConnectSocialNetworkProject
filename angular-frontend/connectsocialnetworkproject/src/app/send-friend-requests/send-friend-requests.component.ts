import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgxImageCompressService } from 'ngx-image-compress';
import { BackendServiceService } from '../backend-service.service';
import { RegistrationUser } from '../registration-user';
import { User } from '../user.model';

@Component({
  selector: 'app-send-friend-requests',
  templateUrl: './send-friend-requests.component.html',
  styleUrls: ['./send-friend-requests.component.css']
})
export class SendFriendRequestsComponent implements OnInit {

     user!: RegistrationUser;
     users!: Array<RegistrationUser>;

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

   this.backendService.getUsersExceptMe().subscribe({
       next: c => {  
           this.users = c;
           //console.log(c);
       },
       error: er => {
           //console.error(er.error.message);
       }
  })

  }

  sendRequest(user: RegistrationUser) {
    
        this.backendService.sendFriendRequest({sentFor: user.username}).subscribe({
       next: c => {  
           this.backendService.getUsersExceptMe().subscribe({
       next: c => {  
           this.users = c;
           //console.log(c);
       },
       error: er => {
           //console.error(er.error.message);
       }
  })
       },
       error: er => {
           //console.error(er.error.message);
       }
  })


  }

}
