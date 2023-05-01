import {Accommodation} from "./accommodation.model";
import {Review} from "./review.model";

export interface Reservation {
  id: number
  from: number;
  end: number;
  guests: number;
  price: number;
  accommodation: Accommodation;
  review: Review;
}
