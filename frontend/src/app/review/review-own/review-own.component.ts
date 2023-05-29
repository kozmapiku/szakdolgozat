import {Component, OnInit} from '@angular/core';
import {ReviewService} from "../../service/review.service";
import {Review} from "../../model/review.model";

@Component({
	selector: 'app-my-reviews',
	templateUrl: './review-own.component.html',
	styleUrls: ['./review-own.component.css']
})
export class ReviewOwnComponent implements OnInit {

	reviews!: Review[];

	constructor(public reviewService: ReviewService) {
	}

	ngOnInit(): void {
		this.getReviews();
	}

	private getReviews() {
		this.reviewService.getMyReview().subscribe({
			next: (data) => {
				this.reviews = data.data;
			},
			error: (error) => {
				alert(error.error.error)
			}
		});
	}
}
