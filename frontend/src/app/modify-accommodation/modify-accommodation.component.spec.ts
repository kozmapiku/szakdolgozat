import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ModifyAccommodationComponent} from './modify-accommodation.component';

describe('ModifyAccommodationComponent', () => {
  let component: ModifyAccommodationComponent;
  let fixture: ComponentFixture<ModifyAccommodationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModifyAccommodationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ModifyAccommodationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
