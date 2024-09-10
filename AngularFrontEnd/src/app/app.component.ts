import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AdminViewComponent} from "./admin-view/admin-view.component";
import {JwtHelperService} from "@auth0/angular-jwt";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoginComponent, AdminViewComponent, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'AngularFrontEnd';
  isLoggedIn = false;
  isAdmin = false;

  constructor(private jwtHelperService: JwtHelperService) {}

  ngOnInit(): void {
    this.checkUserRole();
  }

  checkUserRole() {
    const token = localStorage.getItem('authToken');
    if (token && !this.jwtHelperService.isTokenExpired(token)) {
      const decodedToken = this.jwtHelperService.decodeToken(token);
      const userRole = decodedToken.sub;
      this.isLoggedIn = true;
      if (userRole === 'admin') {
        this.isAdmin = true;
      }
    }
  }

  updateState() {
    this.checkUserRole();
  }
}
