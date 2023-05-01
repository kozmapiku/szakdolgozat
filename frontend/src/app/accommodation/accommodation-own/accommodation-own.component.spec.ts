import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccommodationOwnComponent} from './accommodation-own.component';

describe('MyAccommodationsComponent', () => {
  let component: AccommodationOwnComponent;
  let fixture: ComponentFixture<AccommodationOwnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationOwnComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccommodationOwnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
