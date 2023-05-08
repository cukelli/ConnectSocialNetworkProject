import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { BackendServiceService } from '../backend-service.service';
import { Router } from '@angular/router';

interface ErrorMessages {
  firstName?: string;
  lastName?: string;
  email?: string;
  username?: string;
  password?: string;
  repeatPassword?: string;
}


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})


export class RegistrationComponent implements OnInit {  
  registrationForm!: FormGroup;
  errorMessages: ErrorMessages = {};

  constructor (private fb: FormBuilder, private backendService: BackendServiceService,
    private router: Router ) {}

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
        firstName: ['',Validators.required],
        lastName: ['',Validators.required],
        email: ['',[Validators.required,Validators.email]],
        username: ['',Validators.required],
        password: ['',Validators.required],
        repeatPassword: ['',Validators.required]

    })

  }

async submitForm() {
  console.log(this.registrationForm.value);
    this.backendService.registration(this.registrationForm.value).subscribe({
      next: response => {
        console.log('Registration successful');
        this.router.navigate(['registrationsuccess'], { queryParams: { registrationSuccess: true } }).then(() => {
          setTimeout(() => {
            this.router.navigate(['login']);
          },2000)
          
        });
      },
      error: error => {
          console.error(error);
          console.log(error.error); 
       // this.errorMessages = error.error;

      }
    });
}
  }


