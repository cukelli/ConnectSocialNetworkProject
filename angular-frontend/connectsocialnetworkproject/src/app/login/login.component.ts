import { Component,OnInit } from '@angular/core';
import { FormGroup,FormBuilder } from '@angular/forms';
import { BackendServiceService } from '../backend-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit 
{
  form!: FormGroup;
  constructor(private fb: FormBuilder, private loginService: BackendServiceService) {}
  ngOnInit(): void {
    this.form = this.fb.group({
      username: [''],
      password: ['']
    }) 
  }

  formControl() {
     this.loginService.login(this.form.value).subscribe({
   next: c => {
    
   },
   error: er => console.error(er.error.message)
});
  }

}
