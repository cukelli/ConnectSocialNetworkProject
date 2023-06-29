import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BackendServiceService } from '../backend-service.service';
import { RegistrationUser } from '../registration-user';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { NgxImageCompressService } from 'ngx-image-compress';
import { UserUpdate } from '../userUpdate';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
    user!: RegistrationUser;
    selectedImage!: File;
    isProfileUpdated: boolean = false;
    imageToShow!: String;



  constructor(private fb: FormBuilder,private http: HttpClient,private backendService: BackendServiceService,
     private imageCompressService: NgxImageCompressService,
        private domSanitizer: DomSanitizer) {
    }
  ngOnInit(): void {
  
       this.backendService.getUser().subscribe({
       next: c => {  
           this.user = c;
           //console.log(this.user)
       },
       error: er => {
           //console.error(er.error.message);
       }
   });
  }


   updateUser(): void {
    if (this.selectedImage) {
    const reader = new FileReader();
    reader.onload = () => {
      const imageBase64 = reader.result as string;
      this.imageCompressService.compressFile(imageBase64, -1, 7, 17).then((compressedImage: string) => {
        const userUpdateData: UserUpdate = {
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          username: this.user.username,
          email: this.user.email,
          image: compressedImage
        };
        this.imageToShow = compressedImage;
        this.updateUserWithImage(userUpdateData);
      });
    };
    reader.readAsDataURL(this.selectedImage);
  } else {
    const userUpdateData: UserUpdate = {
        firstName: this.user.firstName,
          lastName: this.user.lastName,
          username: this.user.username,
          email: this.user.email,
          image: ""
    };
    this.updateUserWithImage(userUpdateData);
    console.log("succesfuly updated user without image")
  }

    }


     updateUserWithImage(userUpdateData: UserUpdate): void {
  this.backendService.updateUser(userUpdateData).subscribe({
    next: () => {
      this.isProfileUpdated = true;
      setTimeout(() => {
        this.isProfileUpdated = false;
      }, 5000);
    },
    error: er => {
      // Handle the error
    }
  });
}


 onImageSelected(event: any) {
      this.selectedImage = event.target.files[0];
    }





// updateUser() {

//   const updatedUserData = {
//     firstName: this.user.firstName,
//     lastName: this.user.lastName,
//     username: this.user.username,
//     email: this.user.email
//   };

//   this.backendService.updateUser(updatedUserData).subscribe({
//     next: c => {
//       this.isProfileUpdated = true;
//       setTimeout(() => {
//         this.isProfileUpdated = false;
//       }, 5000);
//     },
//     error: er => {
//       console.error(er.error.message);
//     }
//   });
// }

  }
