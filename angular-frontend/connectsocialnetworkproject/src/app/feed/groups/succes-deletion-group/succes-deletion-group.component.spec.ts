import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccesDeletionGroupComponent } from './succes-deletion-group.component';

describe('SuccesDeletionGroupComponent', () => {
  let component: SuccesDeletionGroupComponent;
  let fixture: ComponentFixture<SuccesDeletionGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuccesDeletionGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuccesDeletionGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
