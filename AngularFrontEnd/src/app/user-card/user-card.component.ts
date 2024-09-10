import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RoleSelectorComponent} from "../role-selector/role-selector.component";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {Role} from "../../models/role";

@Component({
  selector: 'user-card',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RoleSelectorComponent
  ],
  templateUrl: './user-card.component.html',
  styleUrl: './user-card.component.css'
})
export class UserCardComponent implements OnInit{

  @Input() user!: User;
  @Input() roles!: Role[];
  @Output() selectedRole: Role | null = null;
  @Output() userListUpdated = new EventEmitter<void>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    if (this.user && this.roles) {
      this.selectedRole = this.roles.find(role => role.name === this.user.role) || null;
    }
  }

  onRoleSelected(role: Role): void {
    this.selectedRole = role;
    this.user.role = role.name;
  }

  updateUser(): void {
    this.userService.updateUser(this.user).subscribe(
      response => {
        this.userListUpdated.emit();
        console.log('User updated successfully:', response);
      },
      error => {
        console.error('Error updating user:', error);
      }
    );
  }

  deleteUser(): void {
    this.userService.deleteUser(this.user.id).subscribe(
      response => {
        this.userListUpdated.emit();
        console.log('User deleted successfully:', response);
      },
      error => {
        console.error('Error deleted user:', error);
      }
    );
  }
}
