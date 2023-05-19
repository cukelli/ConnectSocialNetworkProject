import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDeletedComponent } from './post-deleted.component';

describe('PostDeletedComponent', () => {
  let component: PostDeletedComponent;
  let fixture: ComponentFixture<PostDeletedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostDeletedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostDeletedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
