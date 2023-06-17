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
import { SuccesDeletionGroupComponent } from './feed/groups/succes-deletion-group/succes-deletion-group.component';
import { PostDetailsComponent } from './feed/posts/post-details/post-details.component';
import { PostDeletedComponent } from './feed/posts/post-deleted/post-deleted.component';
import { CreatePostComponent } from './feed/posts/create-post/create-post.component';
import { CreateGroupComponent } from './feed/groups/create-group/create-group.component';
import { CommentDetailsComponent } from './feed/posts/post-details/comments/comment-details/comment-details.component';
import { CommentDeletedComponent } from './comments/comment-deleted/comment-deleted.component';
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
  {path: 'success-deletion-group',component: SuccesDeletionGroupComponent},
  {path: 'post-details', component: PostDetailsComponent},
  {path: 'post-deleted', component: PostDeletedComponent},
   {path: 'add-post', component: CreatePostComponent},
   {path: 'add-group', component: CreateGroupComponent},
   {path: 'comment-details', component: CommentDetailsComponent},
  {path: 'comment-deleted', component: CommentDeletedComponent}

  ];

  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
