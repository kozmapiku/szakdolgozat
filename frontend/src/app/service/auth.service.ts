import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {Response} from "../rest/response.model";
import {TokenStorageService} from "./token-storage.service";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    authenticated = false;
    baseUrl = environment.baseUrl + "/auth";

    constructor(private http: HttpClient, private router: Router, private tokenStorage: TokenStorageService) {
    }

    public login(email: string, password: string) {
        const body = {'email': email, 'password': password};
        return this.http.post<Response<User>>(this.baseUrl + "/login", body);
    }

    public register(email: string, firstName: string, lastName: string, password: string) {
        const body = {'email': email, 'firstName': firstName, 'lastName': lastName, 'password': password};
        return this.http.post<Response<string>>(this.baseUrl + "/register", body);
    }

    public update(email: string, firstName: string, lastName: string, currentPassword: string, newPassword: string) {
        const body = {
            'email': email,
            'firstName': firstName,
            'lastName': lastName,
            'password': currentPassword,
            "newPassword": newPassword
        };
        return this.http.post<Response<string>>(this.baseUrl + "/update", body);
    }

    public getUserData() {
        return this.http.get<Response<User>>(this.baseUrl + "/user");
    }

  public logout() {
    this.authenticated = false;
    this.tokenStorage.logout();
    this.router.navigateByUrl("/");
  }

  public readUser() {
    if (this.tokenStorage.tokenExpired()) {
      this.authenticated = true;
    }
  }

  public getUserName() {
    return this.tokenStorage.getUser().lastName + ' ' + this.tokenStorage.getUser().firstName;
  }

  public getUserMail() {
    return this.tokenStorage.getUser().email;
  }


}
