import {AnnounceDate} from "./announce_date.model";

export interface Accommodation {
  id: number
  name: string;
  city: string;
  mainImage: string;
  mainImageIndex: number;
  address: string;
  max_guests: number;
  announceDateList: AnnounceDate[];
  listOfImages: string[];

}
