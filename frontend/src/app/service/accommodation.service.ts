import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Accommodation} from "../model/accommodation.model";
import {Response} from "../rest/response.model";
import {environment} from "../../environments/environment";
import {ResponseList} from "../rest/response-list";
import {AccommodationFilter} from "../model/accommodation_filter.model";

@Injectable({
	providedIn: 'root'
})
export class AccommodationService {

	baseUrl = environment.baseUrl + "/accommodation";

	constructor(private http: HttpClient, private router: Router) {
	}

	public newAccommodation(formData: FormData) {
		return this.http.post<Response<string>>(this.baseUrl + "/create", formData);
	}

	public getAll() {
		console.log("url: ", this.baseUrl)
		return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/all");
	}

	public getDetails(id: any) {
		let queryParams = new HttpParams().append("id", id);
		return this.http.get<Response<Accommodation>>(this.baseUrl + "/detail", {params: queryParams});
	}


	public getMyAccommodations() {
		return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/own");
	}

	public deleteAccommodation(id: number) {
		return this.http.post<Response<string>>(this.baseUrl + "/delete", id);
	}

	public modifyAccommodation(formData: FormData) {
		let body = formData
		return this.http.post<Response<string>>(this.baseUrl + "/update", body);
	}

	public getAccommodations(filter: AccommodationFilter) {
		return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/all", {params: <any>filter});
	}
}
