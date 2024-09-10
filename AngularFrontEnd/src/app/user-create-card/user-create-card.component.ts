import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RoleSelectorComponent} from "../role-selector/role-selector.component";
import {Role} from "../../models/role";
import {UserService} from "../../services/user.service";
import {FormsModule} from "@angular/forms";
import {CreateUser} from "../../models/create-user";

@Component({
  selector: 'user-create-card',
  standalone: true,
  imports: [
    RoleSelectorComponent,
    FormsModule
  ],
  templateUrl: './user-create-card.component.html',
  styleUrl: './user-create-card.component.css'
})
export class UserCreateCardComponent implements OnInit{

  user: CreateUser = { username: '', email: '', password: '', role: '' };
  @Input() roles!: Role[];
  @Output() selectedRole: Role | null = null;
  @Output() userCreated = new EventEmitter<void>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    setTimeout(()=>{
      this.selectedRole = this.roles[1];
    },200);
  }

  onRoleSelected(role: Role): void {
    this.selectedRole = role;
    this.user.role = role.name;
  }

  createUser(): void {
    if (this.user.username && this.user.email && this.user.password && this.user.role) {
      this.userService.createUser(this.user).subscribe(
        response => {
          this.userCreated.emit();
          this.resetForm();
          console.log('User created successfully:', response);
        },
        error => {
          console.error('Error creating user:', error);
        }
      );
    } else {
      console.error('All fields are required');
    }
  }

  private resetForm(): void {
    this.user = { username: '', email: '', password: '', role: '' };
  }
}
