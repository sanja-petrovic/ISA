
import { AbstractControl, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function SchedulingDateValidator(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        let tomorow : Date = new Date();
        tomorow.setDate(tomorow.getDate()+1);
        return new Date(control.value) < tomorow ? {dateValidation: {value: control.value}} : null;
      };
}