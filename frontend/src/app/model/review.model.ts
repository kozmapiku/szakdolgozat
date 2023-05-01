import {Accommodation} from "./accommodation.model";

export interface Review {
  comment: string;
  star: number;
  user_name: string;
  accommodation: Accommodation;
}
