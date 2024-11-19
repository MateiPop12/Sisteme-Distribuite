import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {Device} from "../../models/device";
import {User} from "../../models/user";
import {DeviceService} from "../../services/device.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'device-create-card-user',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './device-create-card-user.component.html',
  styleUrl: './device-create-card-user.component.css'
})
export class DeviceCreateCardUserComponent implements OnInit{

  user: User = {id:0,username:"",email:'',password:'',role:""};
  device: Device = {id:0,name:'',address:'',maxConsumption:0,userId:1};
  @Output() deviceCreated = new EventEmitter<void>();

  constructor(private deviceService: DeviceService,
              private userService: UserService) {}
  ngOnInit() {
    this.getUser();
  }

  getUser(){
    const username = sessionStorage.getItem('LoggedUser');
    this.userService.getUserByUsername(username).subscribe({
      next: (response) => {
        this.user = response;
      },
      error: (error) => {
        console.error('Error getting user id',error);
      }
    });
  }

  createDevice(){
    this.device.userId = this.user.id;
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

  private resetForm() {
    this.device={id:0 ,name:'',address:'',maxConsumption:0,userId:1};
  }
}
