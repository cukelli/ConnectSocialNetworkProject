import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentDeletedComponent } from './comment-deleted.component';

describe('CommentDeletedComponent', () => {
  let component: CommentDeletedComponent;
  let fixture: ComponentFixture<CommentDeletedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentDeletedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentDeletedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
