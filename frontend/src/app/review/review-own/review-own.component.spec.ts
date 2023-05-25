import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewOwnComponent} from './review-own.component';

describe('MyReviewsComponent', () => {
	let component: ReviewOwnComponent;
	let fixture: ComponentFixture<ReviewOwnComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ReviewOwnComponent]
		})
			.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(ReviewOwnComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
