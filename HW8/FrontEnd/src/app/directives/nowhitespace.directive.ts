import {Directive} from '@angular/core';
import {NG_VALIDATORS, Validator, AbstractControl} from "@angular/forms";

@Directive({
    selector: '[appNowhitespace]',
    providers: [
        { provide: NG_VALIDATORS, useExisting: NowhitespaceDirective, multi: true }
    ]
})
export class NowhitespaceDirective implements Validator {

    constructor() {
    }

    validate(control: AbstractControl): { [key: string]: any } {
        let isWhitespace = (control.value || "").trim().length === 0;
        // @ts-ignore
        return isWhitespace ? {'whitespace': 'input only contains whitespace'} : null;
    }

}
