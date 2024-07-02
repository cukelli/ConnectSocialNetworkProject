import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { User } from 'src/app/user.model';
import { Group } from 'src/group';
import { Router } from '@angular/router';
import { GroupIndex } from '../GroupIndex';



@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  groupsSearch: GroupIndex[] = [];
  description: string = '';
  pdfDescription: string = ''
  name: string = '';
  page: number = 0;
  minValue: number = 0;
  maxValue: number = 0;
  size: number = 10;
  groups!: Array<Group>;
  groupIndexes!: Array<GroupIndex>;
  group!: Group;
  user: User;
   constructor(private http: HttpClient,private backendService: BackendServiceService,private router: Router) { 
        this.user = JSON.parse(localStorage.getItem('user') || '{}');

        this.getAllGroupsElastic();
        


  }
  
 ngOnInit(): void {}
 
  isMemberOrAdmin(groupI: GroupIndex): void {
    const groupId = groupI.databaseId == null ? 0: groupI.databaseId;

    this.backendService.getGroupDetails(groupId).subscribe(
      (groupDetails: Group) => {
        let group = groupDetails;
        group.admins = [JSON.stringify(group.admins)];
        this.router.navigate(['/group-detail',group])
       // console.log(groupDetails);
      },
      (error: any) => {
         if (error.status === 401) {
        this.router.navigate(['/group-reqest',groupI]);
      } else {
        //console.error('Error occurred:', error);
      }
      }
    );
  }


  searchGroupsByName(): void {
    this.backendService.searchGroupsByNameBackend(this.name).subscribe(
      (data: GroupIndex[]) => {
        this.groupsSearch = data;
        this.groupIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }


  searchGroupsByPostRange(): void {
    this.backendService.getGroupsByPostRange(this.minValue, this.maxValue).subscribe(
      (data: GroupIndex[]) => {
        this.groupsSearch = data;
        this.groupIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }

  
  searchGroupsByDescription(): void {
    this.backendService.searchGroupsByDescriptionBackend(this.description, this.pdfDescription).subscribe(
      (data: GroupIndex[]) => {
        this.groupsSearch = data;
        this.groupIndexes = data; 
      },
      (error: any) => {
        console.error('Error occurred:', error);
      }
    );
  }

getAllGroups() {
  this.backendService.getGroups().subscribe({
    next: (data: any)=> {  
       this.groups = data;
       
    },
    error: er => {
       //  console.error(er.error.message);
    }
});  }


getAllGroupsElastic() {
  this.backendService.getGroupsElastic().subscribe({
    next: (data: any)=> {  
       this.groupIndexes = data;
       
    },
    error: er => {
       //  console.error(er.error.message);
    }
});  }

  
}
