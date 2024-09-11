import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceCreateCardComponent } from './device-create-card.component';

describe('DeviceCreateCardComponent', () => {
  let component: DeviceCreateCardComponent;
  let fixture: ComponentFixture<DeviceCreateCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeviceCreateCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceCreateCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
