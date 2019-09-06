import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CMigaPanComponent } from './c-miga-pan.component';

describe('CMigaPanComponent', () => {
  let component: CMigaPanComponent;
  let fixture: ComponentFixture<CMigaPanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CMigaPanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CMigaPanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
