import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccommodationFilterFormComponent} from './accommodation-filter-form.component';

describe('AccommodationFilterFormComponent', () => {
	let component: AccommodationFilterFormComponent;
	let fixture: ComponentFixture<AccommodationFilterFormComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [AccommodationFilterFormComponent]
		})
			.compileComponents();

		fixture = TestBed.createComponent(AccommodationFilterFormComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
