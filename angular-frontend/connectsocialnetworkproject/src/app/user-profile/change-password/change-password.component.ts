import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BackendServiceService } from 'src/app/backend-service.service';
import { RegistrationUser } from 'src/app/registration-user';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  

    form!: FormGroup;


  constructor(private fb: FormBuilder,private http: HttpClient,
    private backendService: BackendServiceService) {
    }
  ngOnInit(): void {
     this.form = this.fb.group({
      oldPassword: [''],
      newPassword: ['']
    }) 

  }

  changePassword() {
     this.backendService.changePasswordUser(this.form.value).subscribe({
       next: c => {
        console.log(c)  
       },
       error: er => {
           console.error(er.error.message);
       }
   });
  }

    


}
