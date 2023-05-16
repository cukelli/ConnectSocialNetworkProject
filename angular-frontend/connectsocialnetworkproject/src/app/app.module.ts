import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
import { RegistrationSuccesfulComponent } from './registration-succesful/registration-succesful.component';
import { ChangePasswordComponent } from './user-profile/change-password/change-password.component';
import { PostsComponent } from './feed/posts/posts.component';
import { GroupsComponent } from './feed/groups/groups.component';

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
    GroupsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
