import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupReqestComponent } from './group-reqest.component';

describe('GroupReqestComponent', () => {
  let component: GroupReqestComponent;
  let fixture: ComponentFixture<GroupReqestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupReqestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupReqestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
