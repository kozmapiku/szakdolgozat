import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Response} from "../rest/response.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient, private router: Router) {
  }

  public review(accommodationId: number, reservationId: number, reviewStars: number, description: string) {
    let body = {
      "accommodationId": accommodationId,
      "reservationId": reservationId,
      "reviewStars": reviewStars,
      "description": description
    };
    return this.http.post<Response<string>>(this.baseUrl + "/review", body);
  }
}
