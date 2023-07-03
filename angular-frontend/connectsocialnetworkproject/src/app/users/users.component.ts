import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgxImageCompressService } from 'ngx-image-compress';
import { BackendServiceService } from '../backend-service.service';
import { RegistrationUser } from '../registration-user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

      user!: RegistrationUser;
     friends!: Array<RegistrationUser>;


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

  this.backendService.getUserFriends().subscribe({
       next: c => {  
           this.friends = c;
           console.log(c);
       },
       error: er => {
           //console.error(er.error.message);
       }
  })

  }
}