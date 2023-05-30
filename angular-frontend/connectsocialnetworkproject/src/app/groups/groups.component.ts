import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { User } from 'src/app/user.model';
import { Group } from 'src/group';
import { Router } from '@angular/router';



@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  groups!: Array<Group>;
  group!: Group;
  user: User;
   constructor(private http: HttpClient,private backendService: BackendServiceService,private router: Router) { 
        this.user = JSON.parse(localStorage.getItem('user') || '{}');
        
  this.backendService.getGroups().subscribe({
       next: (data: any)=> {  
          this.groups = data;
          
       },
       error: er => {
          //  console.error(er.error.message);
       }
   });  

  }
  
 ngOnInit(): void {}
 
  isMemberOrAdmin(group: Group): void {
    const groupId = group.groupId;

    this.backendService.getGroupDetails(groupId).subscribe(
      (groupDetails: Group) => {
        group = groupDetails;
        group.admins = [JSON.stringify(group.admins)];
        this.router.navigate(['/group-detail',group])
       // console.log(groupDetails);
      },
      (error: any) => {
         if (error.status === 401) {
        this.router.navigate(['/group-reqest',group]);
      } else {
        //console.error('Error occurred:', error);
      }
      }
    );
  }

  
}
