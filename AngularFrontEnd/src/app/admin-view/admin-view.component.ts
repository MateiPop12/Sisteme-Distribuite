import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UserCreateCardComponent} from "../user-create-card/user-create-card.component";
import {UserCardComponent} from "../user-card/user-card.component";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {NgForOf} from "@angular/common";
import {Role} from "../../models/role";
import {RoleService} from "../../services/role.service";
import {FormsModule} from "@angular/forms";
import {DeviceCreateCardComponent} from "../device-create-card/device-create-card.component";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {DeviceCardComponent} from "../device-card/device-card.component";
import {NavBarComponent} from "../nav-bar/nav-bar.component";

@Component({
  selector: 'admin-view',
  standalone: true,
  imports: [
    UserCreateCardComponent,
    UserCardComponent,
    NgForOf,
    FormsModule,
    DeviceCreateCardComponent,
    DeviceCardComponent,
    NavBarComponent
  ],
  templateUrl: './admin-view.component.html',
  styleUrl: './admin-view.component.css'
})
export class AdminViewComponent implements OnInit{

  @Output() users: User[] = [];
  @Output() roles: Role[] = [];
  @Output() devices: Device[] = [];

  constructor(private userService: UserService,
              private roleService: RoleService,
              private deviceService: DeviceService) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadRoles();
    this.loadDevices();
  }

  //----Loading Methods----//
  loadUsers(): void {
    this.userService.getAllUsers().subscribe(
      users => this.users = users,
      error => console.error('Error loading users:', error)
    );
  }

  loadRoles(): void {
    this.roleService.getAllRoles().subscribe(
      roles => this.roles = roles,
      error => console.error('Error loading roles:', error)
    );
  }

  loadDevices():void{
    this.deviceService.getAllDevices().subscribe(
      roles => this.devices = roles,
      error => console.error('Error loading devices:', error)
    )
  }

  reload(): void {
    this.loadUsers();
    this.loadDevices();
  }
}
