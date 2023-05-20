import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './home-page/header/header.component';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import { HeaderRegisteredUserComponent } from './header-registered-user/header-registered-user.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { FeedComponent } from './feed/feed.component';
import { FooterComponent } from './footer/footer.component';
import { PostDeletedComponent } from './feed/posts/post-deleted/post-deleted.component';
import { RegistrationSuccesfulComponent } from './registration-succesful/registration-succesful.component';
import { ChangePasswordComponent } from './user-profile/change-password/change-password.component';
import { PostsComponent } from './feed/posts/posts.component';
import { GroupsComponent } from './groups/groups.component';
import { SuccesDeletionGroupComponent } from './feed/groups/succes-deletion-group/succes-deletion-group.component';
import { GroupReqestComponent } from './groups/group-reqest/group-reqest.component';
import { CreateGroupComponent } from './groups/create-group/create-group.component';
import { GroupDetailComponent } from './groups/group-detail/group-detail.component';
import { PostDetailsComponent } from './feed/posts/post-details/post-details.component';
import { CreatePostComponent } from './feed/posts/create-post/create-post.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomePageComponent,
    HeaderComponent,
    RegistrationComponent,
    HeaderRegisteredUserComponent,
    UserProfileComponent,
    FeedComponent,
    FooterComponent,
    RegistrationSuccesfulComponent,
    ChangePasswordComponent,
    PostsComponent,
    GroupsComponent,
    CreateGroupComponent,
    GroupDetailComponent,
    SuccesDeletionGroupComponent,
    PostDetailsComponent,
    GroupReqestComponent,
    PostDeletedComponent,
    CreatePostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
