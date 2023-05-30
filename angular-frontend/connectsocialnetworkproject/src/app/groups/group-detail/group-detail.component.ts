import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackendServiceService } from 'src/app/backend-service.service';
import { HeaderComponent } from 'src/app/home-page/header/header.component';
import { Group } from 'src/group';
import { Router } from '@angular/router';

@Component({
  selector: 'app-group-detail',
  templateUrl: './group-detail.component.html',
  styleUrls: ['./group-detail.component.css']
})
export class GroupDetailComponent implements OnInit {
    isAdmin: boolean = false;
    group!: Group;
    admins!: Array<any>;
    isGroupUpdated: boolean = false;


  constructor(private backendService: BackendServiceService, private router: ActivatedRoute,
    private routing: Router) {
  this.router.params.subscribe(params => {
    let obj = JSON.parse(JSON.stringify(params));
    this.group = obj;
    this.admins = JSON.parse(obj.admins)
    console.log(this.admins)
    console.log(obj);
  });  
  }
  ngOnInit(): void {
      this.backendService.checkMembership(this.group['groupId']).subscribe({
       next: (c: any) => {
        this.isAdmin = c;
       },
       error: er => {
        console.error;

       }
   });
  }

  deleteGroup(): void {
      this.backendService.deleteGroup(this.group['groupId']).subscribe({
       next: () => {
      this.routing.navigate(['/success-deletion-group'],{ queryParams: { deleteSuccess: true } }).then(() => {
          setTimeout(() => {
            this.routing.navigate(['feed']);
          },2000)
        });
       },
       error: er => {
      //console.error('Error deleting group:', er);

       }
   });
  }

    updateGroup(): void {
      const updatedGroupData = {
      name: this.group.name,
      description: this.group.description
  };
      this.backendService.updateGroup(this.group['groupId'],updatedGroupData).subscribe({
       next: (updatedGroup: Group) => {
       this.isGroupUpdated = true; 
        setTimeout(() => {
      this.isGroupUpdated = false;
    }, 5000);     
       },
       error: er => {

       }
   });
  }

}
