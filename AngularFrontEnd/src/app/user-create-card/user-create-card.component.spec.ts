import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCreateCardComponent } from './user-create-card.component';

describe('UserCreateCardComponent', () => {
  let component: UserCreateCardComponent;
  let fixture: ComponentFixture<UserCreateCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserCreateCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserCreateCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
