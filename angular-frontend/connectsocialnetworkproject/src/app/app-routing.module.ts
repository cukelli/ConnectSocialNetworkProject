import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HeaderComponent } from './home-page/header/header.component';
import { RegistrationComponent } from './registration/registration.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { AuthGuard } from './authorization.service';
import { FeedComponent } from './feed/feed.component';

const routes: Routes = [
  {path: '',component: HomePageComponent,
//  children: [  { path: 'login',component: LoginComponent }
// ]
},
{ path: 'login',component: LoginComponent },
{ path: 'registration', component: RegistrationComponent},
{ path: 'profile', component: UserProfileComponent, canActivate: [AuthGuard]},
{ path: 'feed',component:FeedComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
