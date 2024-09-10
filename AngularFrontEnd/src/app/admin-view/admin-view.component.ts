import {Component, OnInit, Output} from '@angular/core';
import {UserCreateCardComponent} from "../user-create-card/user-create-card.component";
import {UserCardComponent} from "../user-card/user-card.component";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {NgForOf} from "@angular/common";
import {Role} from "../../models/role";
import {RoleService} from "../../services/role.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'admin-view',
  standalone: true,
  imports: [
    UserCreateCardComponent,
    UserCardComponent,
    NgForOf,
    FormsModule
  ],
  templateUrl: './admin-view.component.html',
  styleUrl: './admin-view.component.css'
})
export class AdminViewComponent implements OnInit{

  @Output() users: User[] = [];
  @Output() roles: Role[] = [];

  constructor(private userService: UserService,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadRoles();
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

  //----Reloading Methods----//
  reloadList(): void {
    this.loadUsers();
  }

}
