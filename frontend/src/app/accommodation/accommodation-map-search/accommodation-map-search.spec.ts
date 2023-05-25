import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccommodationMapSearch} from './accommodation-map-search';

describe('MapSearchComponent', () => {
	let component: AccommodationMapSearch;
	let fixture: ComponentFixture<AccommodationMapSearch>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [AccommodationMapSearch]
		})
			.compileComponents();

		fixture = TestBed.createComponent(AccommodationMapSearch);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
