import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SucesfullyDeletedGroupComponent } from './sucesfully-deleted-group.component';

describe('SucesfullyDeletedGroupComponent', () => {
  let component: SucesfullyDeletedGroupComponent;
  let fixture: ComponentFixture<SucesfullyDeletedGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SucesfullyDeletedGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SucesfullyDeletedGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
