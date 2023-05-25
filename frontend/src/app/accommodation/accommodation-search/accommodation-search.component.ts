import {Component} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Accommodation} from "../../model/accommodation.model";

@Component({
	selector: 'app-home',
	templateUrl: './accommodation-search.component.html',
	styleUrls: ['./accommodation-search.component.css']
})
export class AccommodationSearchComponent {

	accommodations: Accommodation[] = [];

	constructor(private authService: AuthService) {
	}

	getStar(accommodation: Accommodation) {
		return accommodation.reviews.map(review => review.star).reduce((a, b) => a + b, 0) / accommodation.reviews.length;
	}

	public filterAccommodations($event: Accommodation[]) {
		this.accommodations = $event;
	}
}
