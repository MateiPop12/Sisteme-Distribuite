import {Component, Input, OnInit, Output} from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {UserService} from "../../services/user.service";
import {DeviceUser} from "../../models/device-user";
import {User} from "../../models/user";
import {DeviceCreateCardUserComponent} from "../device-create-card-user/device-create-card-user.component";
import {DeviceCardComponent} from "../device-card/device-card.component";
import {NgForOf} from "@angular/common";
import {DeviceCardUserComponent} from "../device-card-user/device-card-user.component";
import {ChatBoxComponent} from "../chat-box/chat-box.component";

@Component({
  selector: 'user-view',
  standalone: true,
  imports: [
    NavBarComponent,
    DeviceCreateCardUserComponent,
    NgForOf,
    DeviceCardUserComponent,
    ChatBoxComponent
  ],
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css'
})
export class UserViewComponent implements OnInit{

  @Input() logUsername!: string;
  @Output() user! : User;
  deviceUser : DeviceUser = {id:0};
  @Output() devices: Device[] = [];

  constructor(private deviceService: DeviceService,
              private userService: UserService) {}

  ngOnInit() {

    this.getUser();
    setTimeout(()=>{
      this.loadDevices();
    },100);
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
