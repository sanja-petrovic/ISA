import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import { Optional } from '@angular/core';

export interface PasswordDialogData {
  oldPass: string;
  oldPassConfirm: string;
  newPass:string,
  newPassConfirm:string;
}

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.css']
})
export class ChangePasswordDialogComponent implements OnInit {
  private passPattern = Validators.pattern(
    /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
  );
  form: FormGroup = this.formBuilder.group({
    oldPass: this.data.oldPass,
    oldPassConfirm: ['',Validators.compose([Validators.required])],
    newPass: ['',Validators.compose([Validators.required,this.passPattern])],
    newPassConfirm: ['',Validators.compose([Validators.required,this.passPattern])]
  },[this.oldPassValidator,this.newPassValidator]
  );
 oldPassValidator(control: FormGroup):ValidationErrors | null{
    const password = control.get('oldPass');
    const confirmation = control.get('oldPassConfirm');
    if (!password || !confirmation || password.value === confirmation.value) {
      return null;
    }
      confirmation.setErrors({oldPassValidator: true});
      return { 'old-password-confirmation-mismatch': true };
  }
  newPassValidator(control: FormGroup):ValidationErrors | null{
    const password = control.get('newPass');
    const confirmation = control.get('newPassConfirm');
    if (!password || !confirmation || password.value === confirmation.value) {
      return null;
    }
      confirmation.setErrors({newPassValidator: true});
      return { 'new-password-confirmation-mismatch': true };
  }
  constructor(
    private formBuilder: FormBuilder,
    @Optional() public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PasswordDialogData,
  ) {}
  
  ngOnInit(): void {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
