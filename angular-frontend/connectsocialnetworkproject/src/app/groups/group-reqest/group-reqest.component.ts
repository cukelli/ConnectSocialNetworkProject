import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { GroupRequest } from 'src/app/groupRequest';
import { Group } from 'src/group';
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'app-group-reqest',
  templateUrl: './group-reqest.component.html',
  styleUrls: ['./group-reqest.component.css']
})
export class GroupReqestComponent implements OnInit {
  group!: Group;
  groupRequest!: GroupRequest;
  isGroupRequestSent: boolean = false;



  constructor(private http: HttpClient,private backendService: BackendServiceService,
     private router: ActivatedRoute) {
    this.router.params.subscribe(params => {
    let obj = JSON.parse(JSON.stringify(params));
    this.group = obj;
    this.groupRequest = { groupId: this.group.groupId };

    console.log(obj);
  });  
    }

  ngOnInit(): void {

    this.backendService.getGroupRequest(this.group.groupId).subscribe({
       next: (groupRequest: GroupRequest) => {
        if (groupRequest) {
                this.isGroupRequestSent = true;
            }
       },
       error: er => {
            console.error('Error occurred while fetching group request:', er);
       }
   });
    
  }
   joinGroup(): void {     
      this.backendService.joinGroup(this.groupRequest).subscribe({
       next: (response: GroupRequest) => {
        console.log("Group request sent succesfuly.")
        this.isGroupRequestSent = true;
       },
       error: er => {
          console.error('Error creating group request.', er);
       }
   });
}
}
