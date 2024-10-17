import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceCreateCardUserComponent } from './device-create-card-user.component';

describe('DeviceCreateCardUserComponent', () => {
  let component: DeviceCreateCardUserComponent;
  let fixture: ComponentFixture<DeviceCreateCardUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeviceCreateCardUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceCreateCardUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
