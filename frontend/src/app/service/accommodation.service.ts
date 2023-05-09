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

  constructor(private http:HttpClient, private router:Router) { }

  public newAccommodation(formData: FormData) {
    return this.http.post<Response<string>>(this.baseUrl + "/create", formData);
  }

  public getAll() {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/all");
  }

  public getFiltered(name: any, address: any, guests: any, from: number, end: number) {
    let queryParams = new HttpParams();
    queryParams = name != null ? queryParams.append("name", name) : queryParams;
    queryParams = address != null ? queryParams.append("address", address) : queryParams;
    queryParams = guests != null ? queryParams.append("guests", guests) : queryParams;
    queryParams = !isNaN(from) ? queryParams.append("from", from) : queryParams;
    queryParams = !isNaN(end) ? queryParams.append("end", end) : queryParams;
    console.log("QueryParams ", queryParams);
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/all", {params: queryParams});
  }

  public getDetails(id: any) {
    let queryParams = new HttpParams().append("id", id);
    return this.http.get<Response<Accommodation>>(this.baseUrl + "/detail", {params: queryParams});
  }


  public reserve(id: number, from: number, end: number, guests: number) {
    let body = {"id": id, "from": from, "end": end, "guests": guests}
    console.log(body)
    return this.http.post<Response<string>>(this.baseUrl + "/reserve", body);
  }

  public getMyAccommodations() {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/own");
  }

  public deleteAccommodation(id: number) {
    let body = {"id": id};
    return this.http.post<Response<string>>(this.baseUrl + "/delete", body);
  }

  public modifyAccommodation(formData: FormData) {
    let body = formData
    console.log(formData)
    return this.http.post<Response<string>>(this.baseUrl + "/update", body);
  }

  public getAccommodations(filter: AccommodationFilter) {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/all", {params: <any>filter});
  }
}
