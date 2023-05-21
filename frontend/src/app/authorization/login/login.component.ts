import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {TokenStorageService} from "../../service/token-storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginValid = true;
  form!: UntypedFormGroup;

  constructor(private fb: UntypedFormBuilder, private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: [null, [Validators.required]]
    });
  }

  public login(form: UntypedFormGroup): void {
    this.authService.login(form.get("email")?.value, form.get("password")?.value).subscribe({
      next: (data) => {
        if (data.status == "OK") {
            console.log(data)
            this.tokenStorage.saveToken(data.data.token);
            this.tokenStorage.saveUser(data.data);
            this.tokenStorage.saveExpire(data.data.
            );
            this.loginValid = true;
            this.authService.authenticated = true;
            this.router.navigateByUrl("/home");
        }
            else {
              this.loginValid = false;
              this.authService.authenticated = false;
            }
        },
        error: (error) => {
          this.loginValid = false;
          this.authService.authenticated = false;
          console.log(error.error);
        }
      }
    )
  }

}
