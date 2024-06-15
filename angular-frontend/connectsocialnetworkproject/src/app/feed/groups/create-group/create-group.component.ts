import { Component, OnInit } from '@angular/core';
import { BackendServiceService } from 'src/app/backend-service.service';
import { updatedGroupData } from 'src/app/updatedGroupData';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {
  groupName!: string;
  groupDescription!: string;
  groupDescriptionPdf!: File;
  isGroupCreated: boolean = false;

  constructor(private backendService: BackendServiceService) { }

  ngOnInit(): void { }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.groupDescriptionPdf = input.files[0];
    }
  }

  createGroup(): void {
    const groupForCreation: updatedGroupData = {
      name: this.groupName,
      description: this.groupDescription
    };

    this.backendService.createGroup(groupForCreation, this.groupDescriptionPdf).subscribe({
      next: () => {
        this.isGroupCreated = true;
        setTimeout(() => {
          this.isGroupCreated = false;
        }, 5000);
        return;
      },
      error: er => {
        console.error('Error creating group', er);
      }
    });
  }

}
