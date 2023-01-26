import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentService } from '../services/AppointmentService';

@Component({
  selector: 'app-medical-staff-qr-code-uploader',
  templateUrl: './medical-staff-qr-code-uploader.component.html',
  styleUrls: ['./medical-staff-qr-code-uploader.component.css']
})
export class MedicalStaffQrCodeUploaderComponent implements OnInit {
  Path : string;
  image;

  constructor(private appointmentService: AppointmentService, private router : Router) {
  }
  ngOnInit(): void {
  }
  localUrl: any[];
    async showPreviewImage(event: any) {
        if (event.target.files && event.target.files[0]) {
            var reader = new FileReader();
            reader.onload = (event: any) => {
                this.localUrl = event.target.result;
            }
            if(event.target.files.length > 0)
            {
              console.log(event.target.files[0].name);
              this.Path = event.target.files[0].name
              this.image = await this.convertToBase64(event.target.files[0]);
            }

            reader.readAsDataURL(event.target.files[0]);
        }
    }
    LoadPage() : void{
      this.appointmentService.uploadQrCode(this.image).subscribe(result => {
        let navigate : string = result;
        this.router.navigate([result]);

      });

    }
    convertToBase64(file) {
    return new Promise((resolve, reject) => {
      const fileReader = new FileReader();
      fileReader.readAsDataURL(file);

      fileReader.onload = () => {
        resolve(fileReader.result);
      };

      fileReader.onerror = (error) => {
        reject(error);
      };
    });
  };

}
