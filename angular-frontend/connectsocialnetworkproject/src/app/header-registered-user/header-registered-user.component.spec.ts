import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderRegisteredUserComponent } from './header-registered-user.component';

describe('HeaderRegisteredUserComponent', () => {
  let component: HeaderRegisteredUserComponent;
  let fixture: ComponentFixture<HeaderRegisteredUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderRegisteredUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderRegisteredUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
