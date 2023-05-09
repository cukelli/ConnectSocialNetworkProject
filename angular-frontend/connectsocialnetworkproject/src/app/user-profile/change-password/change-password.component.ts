import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BackendServiceService } from 'src/app/backend-service.service';
import { RegistrationUser } from 'src/app/registration-user';
import { ResponseMessage } from 'src/app/response-message';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  
    errorMessage!: string;
    errorMessageFlag!: boolean;
    form!: FormGroup;
    passwordChanged!: boolean;
    newPasswordsMatchError!: boolean;
    passwordLengthErrorFlag!: boolean;




  constructor(private fb: FormBuilder,private http: HttpClient,
    private backendService: BackendServiceService) {
      this.errorMessage = '';
      this.errorMessageFlag = false;

    }
  ngOnInit(): void {
     this.form = this.fb.group({
      oldPassword: [''],
      newPassword: [''],
      repeatNewPassword: [''],
      
    }) 

  }

  changePassword() {

    if (this.form.value.newPassword !== this.form.value.repeatNewPassword) {
      this.newPasswordsMatchError = true;
       setTimeout(() => {
      this.newPasswordsMatchError = false;
    }, 5000);
      return;
    }

    if (this.form.value.newPassword.length < 8) {
      this.passwordLengthErrorFlag = true;
      setTimeout(() => {
        this.passwordLengthErrorFlag = false;
      }, 5000);
      return;
    }

     this.backendService.changePasswordUser(this.form.value).subscribe({
       next: c => {
        console.log(c)  
        this.passwordChanged = true;
         setTimeout(() => {
        this.passwordChanged = false;
      }, 5000); 
       },
      error: errorResponse => {
        this.errorMessageFlag = true;
        console.error(errorResponse);
        const errorMessage = errorResponse.error.message;
          setTimeout(() => {
          this.errorMessageFlag = false;
        }, 5000);
        //console.log(errorMessage);
       //console.log(this.errorMessageFlag)
       //  alert(errorMessage);
      }
   });
  }

}
