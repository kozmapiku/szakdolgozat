import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AccommodationService} from "../../service/accommodation.service";
import {Accommodation} from "../../model/accommodation.model";
import {DateRange, MatCalendarCellCssClasses} from "@angular/material/datepicker";
import {MatDialog} from "@angular/material/dialog";
import {ReservationDialogComponent} from "../../reservation/reservation-dialog/reservation-dialog.component";
import {AuthService} from "../../service/auth.service";
import {ReservationDialogData} from "../../model/reservation_dialog.model";
import {ReservationService} from "../../service/reservation.service";

@Component({
	selector: 'app-accommodation-detail',
	templateUrl: './accommodation-detail.component.html',
	styleUrls: ['./accommodation-detail.component.css']
})
export class AccommodationDetailComponent implements OnInit {

	id!: string | null;
	accommodation!: Accommodation;
	selectedDateRange: DateRange<Date> = new DateRange<Date>(null, null);
	guests!: number;
	accommodationImages: Array<object> = [];
	availableDates: [date: Date, price: number][] = [];
	center!: google.maps.LatLngLiteral;

	googleMapsOptions: google.maps.MapOptions = {
		disableDoubleClickZoom: true,
		streetViewControl: false,
	};

	reservationDialogData!: ReservationDialogData;
	price: number = 0;

	constructor(private route: ActivatedRoute,
				private accommodationService: AccommodationService,
				public dialog: MatDialog,
				private router: Router,
				private authService: AuthService,
				private reservationService: ReservationService) {
	}

	ngOnInit(): void {
		this.id = this.route.snapshot.paramMap.get('id');
		this.getDetailsFromServer()
	}

	public async getDetailsFromServer() {
		await this.accommodationService.getDetails(this.id).subscribe({
			next: (data) => {
				console.log(JSON.stringify(data));
				this.accommodation = data.data;
				this.fillUpImages();
				this.center = {lat: this.accommodation.lat, lng: this.accommodation.lng};
				this.calculateAvailableDates();
			},
			error: (error) => {
				console.log("Error " + JSON.stringify(error));
			}
		});
	}

	myFilter = (date: Date): boolean => {
		return this.availableDates
			.some(([d, p]) => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());
	}

	dateClass() {
		return (date: Date): MatCalendarCellCssClasses => {
			const highlightDate = this.availableDates
				.some(([d, p]) => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());
			return highlightDate ? 'available-date' : 'reserved-date';
		};
	}

	onSelectedChange(date: Date | null): void {
		this.price = 0;
		if (date == null) {
			return;
		}
		if (
			this.selectedDateRange &&
			this.selectedDateRange.start &&
			date > this.selectedDateRange.start &&
			!this.selectedDateRange.end &&
			this.dateValid(this.selectedDateRange.start, date)
		) {
			this.selectedDateRange = new DateRange(this.selectedDateRange.start, date);
			this.price = this.calculatePrice();
		} else {
			this.selectedDateRange = new DateRange(date, null);
		}
	}

	dateValid(start: Date, end: Date): boolean {
		let dates = this.getDates(start, end);
		return !dates
			.map(date => this.availableDates
				.some(([d, p]) => d.getDate() === date.getDate() &&
					d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear()))
			.includes(false);
	}

	openReservationDialog() {
		this.reservationDialogData = {
			startDate: this.selectedDateRange.start,
			endDate: this.selectedDateRange.end,
			maxGuests: this.accommodation.maxGuests,
			guests: 0,
			price: this.price
		};
		const dialogRef = this.dialog.open(ReservationDialogComponent, {
			width: '400px',
			data: this.reservationDialogData
		});

		dialogRef.afterClosed().subscribe(result => {
			this.reserve(result);
		});
	}

	dateSelected() {
		return this.selectedDateRange.start != null && this.selectedDateRange.end != null;
	}

	getStar() {
		return this.accommodation.reviews.map(review => review.star).reduce((a, b) => a + b, 0) / this.accommodation.reviews.length;
	}

	reservable() {
		return this.dateSelected() && this.authService.authenticated && !this.ownAccommodation();
	}

	ownAccommodation(): boolean {
		return this.accommodation.owner == this.authService.getUserMail();
	}

	modification() {
	}

	delete() {
		if (confirm("Biztos törölni szeretnéd ezt a szállást?")) {
			this.accommodationService.deleteAccommodation(this.accommodation.id).subscribe({
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
	}

	private getDates(startDate: Date, stopDate: Date) {
		let dateArray = [];
		let currentDate = startDate;
		while (currentDate <= stopDate) {
			if (currentDate > new Date()) {
				dateArray.push(new Date(currentDate));
			}
			currentDate = this.addDay(currentDate);
		}
		return dateArray;
	}

	private fillUpImages() {
		this.accommodation.images.forEach(image => {
			this.accommodationImages.push(
				{
					image: 'data:image/jpeg;base64,' + image,
					thumbImage: 'data:image/jpeg;base64,' + image,
					alt: 'Egy kép a szállásról'
				})
		})
	}

	private getDatesWithPrice(startDate: Date, stopDate: Date, price: number): [Date, number][] {
		let dateArray: [Date, number][] = [];
		let currentDate = startDate;
		while (currentDate <= stopDate) {
			if (currentDate > new Date()) {
				dateArray.push([new Date(currentDate), price]);
			}
			currentDate = this.addDay(currentDate);
		}
		return dateArray;
	}

	private addDay(date: Date): Date {
		let newDate = new Date(date);
		newDate.setDate(date.getDate() + 1)
		return newDate;
	}

	private reserve(reservationData: ReservationDialogData) {
		if (reservationData == null || reservationData.endDate == null || reservationData.startDate == null || reservationData.guests == null) {
			return;
		}
		this.reservationService.reserve(this.accommodation.id, Date.parse(reservationData.startDate.toDateString()),
			Date.parse(reservationData.endDate.toDateString()), reservationData.guests).subscribe({
			next: (data) => {
				console.log(JSON.stringify(data));
				alert(data.data);
				this.router.navigateByUrl("/");
			},
			error: (error) => {
				alert(error.error.error)
				console.log("Error " + JSON.stringify(error));
			}
		})
	}

	private calculateAvailableDates() {
		const del = this.accommodation.reservedDays;
		this.availableDates = this.accommodation.announces
			.flatMap(announceDate => this.getDatesWithPrice(new Date(announceDate.startDate), new Date(announceDate.endDate), announceDate.price))
			.filter(([date, price]) => !del.includes(Date.parse(date.toDateString())));
	}

	private calculatePrice() {
		let dates = this.getDates(this.selectedDateRange.start!, this.selectedDateRange.end!);
		return this.availableDates.filter(([date, price]) =>
			dates.find(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear())
		).map(([date, price]) => price).reduce((a, b) => a + b, 0);
	}
}
