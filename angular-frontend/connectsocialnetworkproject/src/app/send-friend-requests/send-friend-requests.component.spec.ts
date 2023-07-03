import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendFriendRequestsComponent } from './send-friend-requests.component';

describe('SendFriendRequestsComponent', () => {
  let component: SendFriendRequestsComponent;
  let fixture: ComponentFixture<SendFriendRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SendFriendRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendFriendRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
