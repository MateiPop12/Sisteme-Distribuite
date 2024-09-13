import {Component, EventEmitter, Output} from '@angular/core';
import {NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {RegisterUser} from "../../models/registerUser";
import {LoginUser} from "../../models/loginUser";
import {JwtHelperService} from "@auth0/angular-jwt";

@Component({
  selector: 'login',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{

  isRegisterMode = false;
  loginForm: FormGroup;
  loginError: string | null = null;
  @Output() updateState = new EventEmitter<string>();

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private jwtHelperService: JwtHelperService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['']
    });
  }

  toggleMode(){
    this.isRegisterMode = !this.isRegisterMode;
    const emailControl = this.loginForm.get('email');
    if (this.isRegisterMode) {
      emailControl?.setValidators([Validators.required, Validators.email]);
    } else {
      emailControl?.clearValidators();
    }
    emailControl?.updateValueAndValidity();
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const formData = this.loginForm.value;

    if (this.isRegisterMode) {
      this.registerUser(formData);
    } else {
      this.loginUser(formData);
    }
  }

  loginUser(formData: any) {
    const user: LoginUser = {
      username: formData.username,
      password: formData.password
    };

    this.authService.login(user).subscribe({
      next: (response) => {
        const token = response.token;

        const decodedToken = this.jwtHelperService.decodeToken(token);
        const userRole = decodedToken.role;

        localStorage.setItem('authToken', token);
        localStorage.setItem('userRole', userRole);

        this.updateState.emit(formData.username);
      },
      error: (error) => {
        console.error('Login failed', error);
        this.loginError = 'Invalid username or password';
        this.markFieldsAsInvalid();
      }
    });
  }

  registerUser(formData: any) {
    const user: RegisterUser = {
      username: formData.username,
      password: formData.password,
      email: formData.email
    };

    this.authService.register(user).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.loginError = null;
        this.updateState.emit(formData.username);
      },
      error: (error) => {
        console.error('Registration failed', error);
        this.loginError = 'Registration failed, please try again';
      }
    });
  }

  markFieldsAsInvalid() {
    this.loginForm.get('username')?.setErrors({ incorrect: true });
    this.loginForm.get('password')?.setErrors({ incorrect: true });
  }
}
