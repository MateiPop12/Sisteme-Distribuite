import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {Role} from "../../models/role";


@Component({
  selector: 'role-selector',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './role-selector.component.html',
  styleUrl: './role-selector.component.css'
})
export class RoleSelectorComponent implements OnInit{

  @Input() selectedRole!: Role | null;
  @Input() roles!: Role[];
  @Output() roleSelected = new EventEmitter<Role>();


  constructor() { }

  ngOnInit(): void {
    setTimeout(()=>{
      if (this.selectedRole) {
        this.roleSelected.emit(this.selectedRole);
      }
    },200)
  }

  onRoleChange(event: any): void {
    const selectedRoleName = event.target.value;
    const selectedRole = this.roles.find(role => role.name === selectedRoleName);
    if (selectedRole) {
      this.roleSelected.emit(selectedRole);
    }
  }

}
