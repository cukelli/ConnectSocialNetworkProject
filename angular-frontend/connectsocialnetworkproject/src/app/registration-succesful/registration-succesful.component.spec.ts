import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationSuccesfulComponent } from './registration-succesful.component';

describe('RegistrationSuccesfulComponent', () => {
  let component: RegistrationSuccesfulComponent;
  let fixture: ComponentFixture<RegistrationSuccesfulComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrationSuccesfulComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationSuccesfulComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
