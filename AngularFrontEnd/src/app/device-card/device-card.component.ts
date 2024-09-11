import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {User} from "../../models/user";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {Role} from "../../models/role";

@Component({
  selector: 'device-card',
  standalone: true,
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './device-card.component.html',
  styleUrl: './device-card.component.css'
})
export class DeviceCardComponent implements OnInit{
  @Input() users!: User[];
  @Input() device! : Device;
  selectedUser!: User | undefined;
  @Output() updateList= new EventEmitter<void>();

  constructor(private deviceService: DeviceService) {}

  ngOnInit() {
    this.selectedUser=this.users.find(user => user.id === this.device.userId);
  }

  onUserChange(event: any): void {
    const selectedUser = event.target.value;
    this.device.userId = this.users.find(user => user.username === selectedUser)?.id || 0;
  }

  updateDevice():void{
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

  deleteDevice(): void {
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

}
