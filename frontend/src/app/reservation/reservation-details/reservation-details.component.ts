import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ReservationService} from "../../service/reservation.service";
import {Reservation} from "../../model/reservation.model";
import {MatDialog} from "@angular/material/dialog";
import {ReviewDialogComponent} from "../../review/review-dialog/review-dialog.component";
import {ReviewService} from "../../service/review.service";
import {ReservationDialogComponent} from "../reservation-dialog/reservation-dialog.component";
import {ReservationDialogData} from "../../model/reservation_dialog.model";

@Component({
	selector: 'app-reservation-details',
	templateUrl: './reservation-details.component.html',
	styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit {

	id!: string | null;
	reservation!: Reservation;
	reservationImages: Array<object> = [];
	reviewData!: ReviewDialogData;
	reservationDialogData!: ReservationDialogData;

	constructor(private route: ActivatedRoute,
				private reservationService: ReservationService,
				public dialog: MatDialog,
				public reviewService: ReviewService,
				private router: Router) {
	}

	ngOnInit(): void {
		this.id = this.route.snapshot.paramMap.get('id');
		this.getDetailsFromServer()
	}

	public expired() {
		return this.reservation.endDate < Date.parse(new Date().toDateString());
	}

	public review() {
		const dialogRef = this.dialog.open(ReviewDialogComponent, {
			width: '600px',
			data: {}
		});

		dialogRef.afterClosed().subscribe(result => {
			this.reviewData = result;
			if (this.reviewData.starReview != null)
				this.sendReview();
		});
	}

	public edit() {
		this.reservationDialogData = {
			startDate: new Date(this.reservation.startDate),
			endDate: new Date(this.reservation.endDate),
			maxGuests: this.reservation.accommodation.maxGuests,
			guests: this.reservation.guests,
			price: this.reservation.price
		};
		const dialogRef = this.dialog.open(ReservationDialogComponent, {
			width: '400px',
			data: this.reservationDialogData
		});

		dialogRef.afterClosed().subscribe(result => {
			if (result !== undefined)
				this.editReserve(result);
		});
	}

	public delete() {
		this.reservationService.delete(this.reservation.id).subscribe({
			next: (data) => {
				alert(data.data);
				this.router.navigateByUrl("/");
			},
			error: (error) => {
				alert(error.error.error);
			}
		})
	}

	alreadyReviewed() {
		return this.reservation.review != null;
	}

	private getDetailsFromServer() {
		this.reservationService.getDetails(this.id).subscribe({
			next: (data) => {
				console.log(JSON.stringify(data));
				this.reservation = data.data;
				this.fillUpImages();
			},
			error: (error) => {
				console.log("Error " + JSON.stringify(error));
			}
		});
	}

	private sendReview() {
		this.reviewService.review(this.reservation.accommodation.id, this.reservation.id, this.reviewData.starReview, this.reviewData.description).subscribe({
			next: (data) => {

				console.log(JSON.stringify(data));
				alert(data.data);
				this.router.navigateByUrl("/");
			},
			error: (error) => {
				console.log("Error " + JSON.stringify(error));
			}
		})
	}

	private fillUpImages() {
		this.reservation.accommodation.images.forEach(image => {
			this.reservationImages.push(
				{
					image: 'data:image/jpeg;base64,' + image,
					thumbImage: 'data:image/jpeg;base64,' + image,
					alt: 'Kép a szállásról'
				})
		})
	}

	private editReserve(result: any) {
		this.reservationService.updateReservation(this.reservation.id, result?.guests).subscribe({
			next: (data) => {
				console.log(JSON.stringify(data));
				alert(data.data);
			},
			error: (error) => {
				alert(error.error.error)
				console.log("Error " + JSON.stringify(error));
			}
		})
	}
}

export interface ReviewDialogData {
	starReview: number;
	description: string;
}
