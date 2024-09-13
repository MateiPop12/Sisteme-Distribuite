import {Component, EventEmitter, Output} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'nav-bar',
  standalone: true,
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {

  @Output() refresh = new EventEmitter<void>;

  constructor(private authService: AuthService) {}
  logout(){
    this.authService.logout();
    window.location.reload();
  }
}
