import {AnnounceDate} from "./announce_date.model";
import {Review} from "./review.model";

export interface Accommodation {
	id: number
	name: string;
	address: string;
	floor: number;
	door: number;
	lat: number;
	lng: number;
	description: string;
	maxGuests: number;
	owner: string;
	star: number;
	reviews: Review[];
	announces: AnnounceDate[];
	reservedDays: number[];
	images: string[];
	mainImage: string;
}
