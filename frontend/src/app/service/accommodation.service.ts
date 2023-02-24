import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
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

  public newAccommodation(name: string, address: string, maxGuest: number, city: string,
                          announceDateList: any, listOfImages: any) {
    let body = {name, address, maxGuest, city, announceDateList, 'listOfImages': listOfImages}
    console.log()
    console.log(body)
    console.log("listofImages"+listOfImages.length)
    return this.http.post<Response<Accommodation>>(this.baseUrl +"/accommodation/new", body);
  }

  public getCities() {
    return this.http.get<ResponseList<string>>(this.baseUrl + "/city/all");
  }

  public getAll() {
    return this.http.get<ResponseList<Accommodation>>(this.baseUrl + "/accommodation/all");
  }
}
