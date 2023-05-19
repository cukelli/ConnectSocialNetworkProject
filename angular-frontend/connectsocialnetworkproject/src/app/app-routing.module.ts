  import { NgModule } from '@angular/core';
  import { RouterModule, Routes } from '@angular/router';
  import { LoginComponent } from './login/login.component';
  import { HomePageComponent } from './home-page/home-page.component';
  import { HeaderComponent } from './home-page/header/header.component';
  import { RegistrationComponent } from './registration/registration.component';
  import { UserProfileComponent } from './user-profile/user-profile.component';
  import { AuthGuard } from './authorization.service';
  import { FeedComponent } from './feed/feed.component';
  import { RegistrationSuccesfulComponent } from './registration-succesful/registration-succesful.component';
import { GroupDetailComponent } from './groups/group-detail/group-detail.component';
import { GroupReqestComponent } from './groups/group-reqest/group-reqest.component';
import { SucesfullyDeletedGroupComponent } from './feed/groups/sucesfully-deleted-group/sucesfully-deleted-group.component';

  const routes: Routes = [
    {path: '',component: HomePageComponent,
  //  children: [  { path: 'login',component: LoginComponent }
  // ]
  },
  { path: 'registration', component: RegistrationComponent},
  {path: 'registrationsuccess',component: RegistrationSuccesfulComponent},
  { path: 'login',component: LoginComponent },
  { path: 'profile', component: UserProfileComponent, canActivate: [AuthGuard]},
  { path: 'feed',component:FeedComponent},
  {path: 'group-detail',component: GroupDetailComponent},
  {path: 'group-reqest', component: GroupReqestComponent},
  {path: 'success-deletion-group',component: SucesfullyDeletedGroupComponent}
  ];

  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
