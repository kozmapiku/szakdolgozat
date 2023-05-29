import {Component, OnInit} from '@angular/core';
import {Accommodation} from "../../model/accommodation.model";
import {AuthService} from "../../service/auth.service";
import {AccommodationService} from "../../service/accommodation.service";

@Component({
	selector: 'app-my-accommodations',
	templateUrl: './accommodation-own.component.html',
	styleUrls: ['./accommodation-own.component.css']
})
export class AccommodationOwnComponent implements OnInit {

	accommodations: Accommodation[] = [];

	constructor(private authService: AuthService, private accommodationService: AccommodationService) {
	}

	ngOnInit(): void {
		this.getMyAccommodationsFromServer();
	}

	private getMyAccommodationsFromServer() {
		this.accommodationService.getMyAccommodations().subscribe({
			next: (data) => {
				this.accommodations = data.data;
			},
			error: (error) => {
				console.log("Error " + JSON.stringify(error));
			}
		})
	}
}
