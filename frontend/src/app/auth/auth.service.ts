import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {Response} from "../rest/response.model";
import {TokenStorageService} from "./token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated = false;

  constructor(private http:HttpClient, private router:Router, private tokenStorage: TokenStorageService) { }

  public login(email: string, password: string) {
    const body = {'email': email, 'password': password};
    return this.http.post<Response<User>>("http://192.168.0.192:8080/auth/login", body);
  }

  public register(email: string, firstName: string, lastName: string, password: string) {
    const body = {'email': email, 'firstName': firstName, 'lastName': lastName, 'password': password};
    return this.http.post<Response<string>>("http://192.168.0.192:8080/auth/register", body);
  }

  public update(email: string, firstName: string, lastName: string, currentPassword: string, newPassword: string) {
    const body = {
      'email': email,
      'firstName': firstName,
      'lastName': lastName,
      'password': currentPassword,
      "newPassword": newPassword
    };
    return this.http.post<Response<string>>("http://192.168.0.192:8080/auth/update", body);
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

  public getUserMail() {
    return this.tokenStorage.getUser().email;
  }

  public getUserData() {
    return this.http.get<Response<string>>("http://192.168.0.192:8080/auth/user");
  }
}
