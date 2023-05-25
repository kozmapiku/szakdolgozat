import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReservationOwnComponent} from './reservation-own.component';

describe('MyReservationsComponent', () => {
	let component: ReservationOwnComponent;
	let fixture: ComponentFixture<ReservationOwnComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ReservationOwnComponent]
		})
			.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(ReservationOwnComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
