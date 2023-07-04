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
import { FriendRequestsComponent } from './friend-requests/friend-requests.component';
import { UsersComponent } from './users/users.component';
import { SendFriendRequestsComponent } from './send-friend-requests/send-friend-requests.component';
  const routes: Routes = [
    {path: '',component: HomePageComponent,
  //  children: [  { path: 'login',component: LoginComponent }
  // ]
  },
  { path: 'registration', component: RegistrationComponent},
  {path: 'registrationsuccess',component: RegistrationSuccesfulComponent},
  { path: 'login',component: LoginComponent },
  { path: 'profile', component: UserProfileComponent, canActivate: [AuthGuard]},
  { path: 'feed',component:FeedComponent, canActivate: [AuthGuard]},
  {path: 'group-detail',component: GroupDetailComponent, canActivate: [AuthGuard]},
  {path: 'group-reqest', component: GroupReqestComponent, canActivate: [AuthGuard]},
  {path: 'success-deletion-group',component: SuccesDeletionGroupComponent, canActivate: [AuthGuard]},
  {path: 'post-details', component: PostDetailsComponent,canActivate: [AuthGuard]},
  {path: 'post-deleted', component: PostDeletedComponent,canActivate: [AuthGuard]},
   {path: 'add-post', component: CreatePostComponent,canActivate: [AuthGuard]},
   {path: 'add-group', component: CreateGroupComponent,canActivate: [AuthGuard]},
   {path: 'comment-details', component: CommentDetailsComponent,canActivate: [AuthGuard]},
  {path: 'comment-deleted', component: CommentDeletedComponent,canActivate: [AuthGuard]},
  {path: 'friend-requests', component: FriendRequestsComponent,canActivate: [AuthGuard]},
  {path: 'friends', component: UsersComponent,canActivate: [AuthGuard]},
  {path: 'users', component: SendFriendRequestsComponent,canActivate: [AuthGuard]}

  ];

  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
