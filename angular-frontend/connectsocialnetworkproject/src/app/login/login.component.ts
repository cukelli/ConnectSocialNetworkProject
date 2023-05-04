import { Component,OnInit } from '@angular/core';
import { FormGroup,FormBuilder } from '@angular/forms';
import { BackendServiceService } from '../backend-service.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit 
{


  form!: FormGroup;
  constructor(private fb: FormBuilder, private loginService: BackendServiceService,private router:
    Router, authService: AuthorizationService) {
      if (authService.isLoggedIn()){
        this.router.navigate(['profile'])
      }
    }
  ngOnInit(): void {
    this.form = this.fb.group({
      username: [''],
      password: ['']
    }) 
  }
formControl() {
   this.loginService.login(this.form.value).subscribe({
       next: c => {  
           console.log("Welcome user " + this.form.value.username)
                 localStorage.setItem("token", JSON.parse(JSON.stringify(c)).token);
                 this.router.navigate(['profile']);      
       },
       error: er => {
           console.error(er.error.message);
           // set the form controls for both username and password to invalid
           this.form.controls['username'].setErrors({'incorrect': true});
           this.form.controls['password'].setErrors({'incorrect': true});

       }
   });
}

}
