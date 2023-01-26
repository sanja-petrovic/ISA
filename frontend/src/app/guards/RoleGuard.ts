import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import {AuthService} from "../services/AuthService";
import {UserService} from "../services/UserService";

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private readonly auth: AuthService, private readonly router: Router, private readonly userService: UserService) { }
  canActivate(route: ActivatedRouteSnapshot): boolean{
    const expectedRole = route.data['expectedRole'];

    const token = localStorage.getItem('jwt');
    if(!token)
    {
      this.router.navigate(['/login']);
      return false;
    }

    const role = this.userService.getRoleFromToken(localStorage.getItem("jwt"))

    if(role !== expectedRole)
    {
      this.router.navigate(['/']);
      return false;
    }
    return true;

  }

}
