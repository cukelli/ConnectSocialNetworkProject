import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { updatedGroupData } from 'src/app/updatedGroupData';
import { updatedPostData } from 'src/app/updatedPostData';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {

      groupName!: string;
      groupDescription!: string;
      isGroupCreated: boolean = false;


      constructor(private backendService: BackendServiceService) { }

  ngOnInit(): void {
  }

   createGroup(): void {
    const groupForCreation: updatedGroupData = {
      name: this.groupName,
      description: this.groupDescription
    };
      
      this.backendService.createGroup(groupForCreation).subscribe({
       next: () => {
      this.isGroupCreated = true;
       setTimeout(() => {
       this.isGroupCreated = false;
        }, 5000);
        return;     
       },
       error: er => {
          //console.error('Error creating post', er);


       }
   });
  }

}
