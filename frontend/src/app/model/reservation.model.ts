import {Accommodation} from "./accommodation.model";
import {Review} from "./review.model";

export interface Reservation {
	id: number
	startDate: number;
	endDate: number;
	guests: number;
	price: number;
	accommodation: Accommodation;
	review: Review;
	isEnded: boolean
}
