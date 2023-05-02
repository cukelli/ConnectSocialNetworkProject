import { Component, ViewChild, ElementRef, OnInit, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  constructor(
    private router: Router
  ){}
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  navigateTo(link: string){
    if(link)
        this.router.navigate([link]);
  }
}
