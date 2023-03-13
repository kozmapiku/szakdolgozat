import {AnnounceDate} from "./announce_date.model";

export interface Accommodation {
  id: number
  name: string;
  city: string;
  address: string;
  max_guests: number;
  announceDates: AnnounceDate[];

}