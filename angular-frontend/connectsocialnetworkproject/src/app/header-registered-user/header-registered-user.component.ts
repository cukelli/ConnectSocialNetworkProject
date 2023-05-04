import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header-registered-user',
  templateUrl: './header-registered-user.component.html',
  styleUrls: ['./header-registered-user.component.css']
})
export class HeaderRegisteredUserComponent {

  constructor(private router: Router) {}

  logout() {
    localStorage.removeItem('token');
  }


}
