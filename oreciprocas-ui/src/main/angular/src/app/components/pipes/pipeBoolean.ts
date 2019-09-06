import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'booleanConvencion'
})
export class BooleanConvencion implements PipeTransform {
    public transform(value: any) {
        switch (value) {
            case true:
                return 'Si';
            case false:
                return 'No';
            default:
                const applyTag = value.replace('›', '<span class="separator">›</span>');
                return  applyTag;
        }
    }
}
