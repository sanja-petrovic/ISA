
import { AbstractControl, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function ScheduleDurationValidator(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        console.log(control.value)
        console.log(+control.value >= 15 &&  +control.value <= 60)
        return !(+control.value >= 15 &&  +control.value <= 60) ? {durationValidation: {value: control.value}} : null;
      };
}