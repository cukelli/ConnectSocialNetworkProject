import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BackendServiceService } from '../backend-service.service';
import { RegistrationUser } from '../registration-user';
import { FormBuilder, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
    user!: RegistrationUser;
    isProfileUpdated: boolean = false;


  constructor(private fb: FormBuilder,private http: HttpClient,private backendService: BackendServiceService) {
    }
  ngOnInit(): void {
  
       this.backendService.getUser().subscribe({
       next: c => {  
           this.user = c;
           //console.log(this.user)
       },
       error: er => {
           //console.error(er.error.message);
           // set the form controls for both username and password to invalid;
       }
   });
  }

updateUser() {
  const updatedUserData = {
    firstName: this.user.firstName,
    lastName: this.user.lastName,
    username: this.user.username,
    email: this.user.email
  };

  this.backendService.updateUser(updatedUserData).subscribe({
    next: c => {
      this.isProfileUpdated = true;
      setTimeout(() => {
        this.isProfileUpdated = false;
      }, 5000);
    },
    error: er => {
      console.error(er.error.message);
    }
  });
}

  }
