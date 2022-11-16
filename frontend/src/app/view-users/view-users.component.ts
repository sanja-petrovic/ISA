import { Component, OnInit } from '@angular/core';
import { MedicalStaff } from '../model/Users';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { UserService } from '../services/UserService';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {
  users : any[] = [];
  searchParam : string[] = [];
  
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll().subscribe(data => {
      this.users = data;
    })
  }

  searchUsers(): void {
    this.userService.search(this.searchParam).subscribe(data => {
      this.users = data;
    })
  }

}
