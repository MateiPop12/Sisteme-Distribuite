import {Component, Input, OnInit, Output} from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {UserService} from "../../services/user.service";
import {DeviceUser} from "../../models/device-user";
import {User} from "../../models/user";

@Component({
  selector: 'user-view',
  standalone: true,
  imports: [
    NavBarComponent
  ],
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css'
})
export class UserViewComponent implements OnInit{

  @Input() logUsername!: string;
  user! : User;
  deviceUser : DeviceUser = {id:0};
  @Output() devices: Device[] = [];

  constructor(private deviceService: DeviceService,
              private userService: UserService) {}

  ngOnInit() {

    this.getUser();

    setTimeout(()=>{
      console.log("getUser",this.user);
    },100);

    setTimeout(()=>{
      console.log(this.logUsername);
    },100);

    setTimeout(()=>{
      this.loadDevices();
    },100);

    console.log(this.devices);

  }

  getUser(){
    this.userService.getUserByUsername(this.logUsername).subscribe({
      next: (response) => {
        console.log(response);
        this.user = response;
      },
      error: (error) => {
        console.error('Error getting user id',error);
      }
    });
  }

  loadDevices():void{
    this.deviceUser.id = this.user.id;
    this.deviceService.getDevicesByUser(this.deviceUser).subscribe({
      next: (response) => {
        this.devices = response;
        console.log("Devices loaded successfully");
      },
      error: (error) => {
        console.error('Error loading devices:', error)
      }
    });
  }
}
