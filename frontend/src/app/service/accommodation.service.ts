import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Accommodation} from "../model/accommodation.model";
import {Response} from "../rest/response.model";
import {environment} from "../../environments/environment";
import {ResponseList} from "../rest/response-list";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  baseUrl = environment.baseUrl;

  constructor(private http:HttpClient, private router:Router) { }

  public newAccommodation(formData: FormData) {
    let body = formData
    console.log(formData)
    return this.http.post<Response<string>>(this.baseUrl + "/accommodation/new", body);
  }

  public getCities() {
    return this.http.get<ResponseList<string>>(this.baseUrl + "/city/all");
  }

  public getAll() {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/accommodation/all");
  }

  public getFiltered(name: any, guests: any, from: number, end: number) {
    let queryParams = new HttpParams();
    queryParams = name != null ? queryParams.append("name", name) : queryParams;
    queryParams = guests != null ? queryParams.append("guests", guests) : queryParams;
    queryParams = !isNaN(from) ? queryParams.append("from", from) : queryParams;
    queryParams = !isNaN(end) ? queryParams.append("end", end) : queryParams;
    console.log("QueryParams ", queryParams);
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/accommodation/all", {params: queryParams});
  }

  public getDetails(id: any) {
    let queryParams = new HttpParams().append("id", id);
    return this.http.get<Response<Accommodation>>(this.baseUrl + "/accommodation/get_details", {params: queryParams});
  }


  public reserve(id: number, from: number, end: number, guests: number) {
    let body = {"id": id, "from": from, "end": end, "guests": guests}
    return this.http.post<Response<string>>(this.baseUrl + "/accommodation/reserve", body);
  }
}
