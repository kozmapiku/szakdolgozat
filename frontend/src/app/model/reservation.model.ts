import {Accommodation} from "./accommodation.model";

export interface Reservation {
  id: number
  from: number;
  end: number;
  guests: number;
  accommodation: Accommodation;
}
