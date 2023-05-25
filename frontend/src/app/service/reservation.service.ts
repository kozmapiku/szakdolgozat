import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResponseList} from "../rest/response-list";
import {Reservation} from "../model/reservation.model";
import {Response} from "../rest/response.model";

@Injectable({
	providedIn: 'root'
})
export class ReservationService {

	baseUrl = environment.baseUrl + "/reservation";

	constructor(private http: HttpClient, private router: Router) {
	}

	public getMyReservations() {
		return this.http.get<ResponseList<Reservation>>(this.baseUrl + "/own");
	}

	public getDetails(id: any) {
		let queryParams = new HttpParams().append("id", id);
		return this.http.get<Response<Reservation>>(this.baseUrl + "/detail", {params: queryParams});
	}

	public reserve(id: number, from: number, end: number, guests: number) {
		let body = {"accommodationId": id, "startDate": from, "endDate": end, "guests": guests}
		return this.http.post<Response<string>>(this.baseUrl + "/reserve", body);
	}

	public delete(id: number) {
		return this.http.post<Response<string>>(this.baseUrl + "/delete", id);
	}

	public updateReservation(id: number, guests: any) {
		let body = {"id": id, "guests": guests};
		return this.http.post<Response<string>>(this.baseUrl + "/update", body);
	}
}
