
import { AbstractControl, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function ScheduleDurationValidator(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        return !(+control.value >= 15 &&  +control.value <= 60) ? {durationValidation: {value: control.value}} : null;
      };
}