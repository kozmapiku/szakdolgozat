import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResponseList} from "../rest/response-list";
import {Reservation} from "../model/reservation.model";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient, private router: Router) {
  }

  public getMyReservations() {
    return this.http.get<ResponseList<Reservation>>(this.baseUrl + "/reservation/get_owned");
  }
}
