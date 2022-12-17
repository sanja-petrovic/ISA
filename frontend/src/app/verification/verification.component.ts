import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../services/AuthService";

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

  error: string = "";
  verifiedSuccessfully: boolean | null = null;
  constructor(private route: ActivatedRoute,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.verify(this.route.snapshot.paramMap.get('email') as string).subscribe(
      (data) => {
        console.log(data);
          this.verifiedSuccessfully = true;
      },
      (error) => {
      this.verifiedSuccessfully = false;
      console.log(error)
        if(error.status == 404) {
          this.error = "Account does not exist.";
        } else if(error.status == 403) {
          this.error = "Account already verified.";
        }
      });
  }

}
