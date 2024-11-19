import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from "../../models/user";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Device} from "../../models/device";
import {NgForOf} from "@angular/common";
import {DeviceService} from "../../services/device.service";


@Component({
  selector: 'device-create-card',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './device-create-card.component.html',
  styleUrl: './device-create-card.component.css'
})
export class DeviceCreateCardComponent implements OnInit{

  @Input() users!: User[];
  device: Device = {id:0,name:'',address:'',maxConsumption:0,userId:0};
  @Output() deviceCreated = new EventEmitter<void>();

  constructor(private deviceService: DeviceService,) {}
  ngOnInit() {
    this.device.userId = this.users[0].id;
  }

  createDevice(){
    console.log("Create device",this.device);
    if(this.device.name&&this.device.address&&this.device.userId){
      this.deviceService.createDevice(this.device).subscribe(
        respone=>{
          this.deviceCreated.emit();
          this.resetForm();
          console.log('Device created successfully:', respone);
        },
        error=>{
          console.error('Error creating device:', error);
        }
      )
    }
    else {
      console.log("All fields are required");
    }
  }

  onUserChange(event: any): void {
    const selectedUser = event.target.value;
    this.device.userId = this.users.find(user => user.username === selectedUser)?.id || 0;
  }

  private resetForm() {
    this.device={id:0 ,name:'',address:'',maxConsumption:0,userId:1};
  }
}
