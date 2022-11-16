import {Component} from '@angular/core';
import {AuthService} from "./services/AuthService";
import {UserService} from "./services/UserService";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'howtosavealife';
  user = '';
  constructor(
    private authService: AuthService,
    private userService: UserService) {
  }


  isLoggedIn(): boolean {
    return this.authService.tokenIsPresent();
  }

  logOut() {
    this.authService.logout();
  }
}
