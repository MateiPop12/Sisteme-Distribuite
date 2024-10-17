import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";

@Component({
  selector: 'device-card-user',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './device-card-user.component.html',
  styleUrl: './device-card-user.component.css'
})
export class DeviceCardUserComponent {

  @Input() device! : Device;
  @Output() updateList= new EventEmitter<void>();

  constructor(private deviceService: DeviceService) {}


  deleteDevice() {
    this.deviceService.deleteDevice(this.device.id).subscribe(
      response => {
        this.updateList.emit();
        console.log('Device deleted successfully:', response);
      },
      error => {
        console.log('Error deleting device:', error);
      }
    );
  }

  updateDevice() {
    this.deviceService.updateDevice(this.device).subscribe(
      response => {
        this.updateList.emit();
        console.log('Device updated successfully', response);
      },
      error => {
        console.log('Error updating device:', error);
      }
    );
  }
}
