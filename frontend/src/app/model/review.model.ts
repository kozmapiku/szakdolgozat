import {Accommodation} from "./accommodation.model";

export interface Review {
    comment: string;
    star: number;
    reservationId: number;
    accommodationId: number;
    name: string;
    accommodation: Accommodation;
}
