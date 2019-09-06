
import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[OnlyNumber]'
})
export class OnlyNumber {
  regexStr = '^[0-9]+$';
  constructor(private el: ElementRef) { }

  @Input() OnlyNumber: boolean;

  @HostListener('keydown', ['$event']) onKeyDown(event) {
    let e = <KeyboardEvent>event;

    if (this.OnlyNumber && e.altKey != true) {
      if (// permite números del 1 al 9, esc, backspace, tab
        [46, 8, 9, 27, 13, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105].indexOf(e.keyCode) !== -1 ||
        // permite: Ctrl+A 
        (e.keyCode == 65 && e.ctrlKey === true) ||
        // permite: Ctrl+C 
        (e.keyCode == 67 && e.ctrlKey === true) ||
        // permite: Ctrl+V 
        (e.keyCode == 86 && e.ctrlKey === true) ||
        // permite: Ctrl+X 
        (e.keyCode == 88 && e.ctrlKey === true) ||
        // permite: home, end, left, right 
        (e.keyCode >= 35 && e.keyCode <= 39)) {
        // que no haga nada si sucede
        return;
      }
    }
      let ch = String.fromCharCode(e.keyCode);
      let regEx = new RegExp(this.regexStr);
    if (regEx.test(ch) && e.shiftKey != true && e.keyCode != 186 )
      return;
    else
      e.preventDefault();
  }

}


/*
Copyright 2016 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/