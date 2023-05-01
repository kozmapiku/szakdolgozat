import {Component, OnInit} from '@angular/core';
import {ReviewService} from "../service/review.service";
import {Review} from "../model/review.model";

@Component({
  selector: 'app-my-reviews',
  templateUrl: './my-reviews.component.html',
  styleUrls: ['./my-reviews.component.css']
})
export class MyReviewsComponent implements OnInit {

  reviews!: Review[];

  constructor(public reviewService: ReviewService) {
  }

  ngOnInit(): void {
    this.getReviews();
  }

  private getReviews() {
    this.reviewService.getMyReview().subscribe({
      next: (data) => {
        console.log(JSON.stringify(data));
        this.reviews = data.data;
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    });
  }
}
