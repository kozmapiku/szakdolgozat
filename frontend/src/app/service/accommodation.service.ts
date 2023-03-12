import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Accommodation} from "../model/accommodation.model";
import {Response} from "../rest/response.model";
import {environment} from "../../environments/environment";
import {ResponseList} from "../rest/response-list";
import {City} from "../model/city.model";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  baseUrl = environment.baseUrl;

  constructor(private http:HttpClient, private router:Router) { }

  public newAccommodation(formData: FormData) {
    let body = formData
    console.log(JSON.stringify(body))
    return this.http.post<Response<Accommodation>>(this.baseUrl +"/accommodation/new", body);
  }

  public getCities() {
    return this.http.get<ResponseList<string>>(this.baseUrl + "/city/all");
  }

  public getAll() {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/accommodation/all");
  }
}
