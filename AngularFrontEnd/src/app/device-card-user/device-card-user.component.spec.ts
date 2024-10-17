import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceCardUserComponent } from './device-card-user.component';

describe('DeviceCardUserComponent', () => {
  let component: DeviceCardUserComponent;
  let fixture: ComponentFixture<DeviceCardUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeviceCardUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceCardUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
