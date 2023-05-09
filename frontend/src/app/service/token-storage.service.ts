import {Injectable} from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const EXPIRES_KEY = 'auth-expire';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  logout(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public tokenExpired(): boolean {
    let expires = window.sessionStorage.getItem(EXPIRES_KEY);
    if(expires != null) {
      let date = new Date(Number(expires));
      let now = new Date();
      if(date > now) {
        return true;
      }
    }
    return false;
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }

  public saveExpire(expiresIn: number) {
    window.sessionStorage.removeItem(EXPIRES_KEY);
    window.sessionStorage.setItem(EXPIRES_KEY, expiresIn.toString());
  }
}
