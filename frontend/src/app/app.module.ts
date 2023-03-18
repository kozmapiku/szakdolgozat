import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RoutingModule} from './routing/routing.module';
import {HomeComponent} from './home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material/material.module';
import {LayoutComponent} from './layout/layout.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {HeaderComponent} from './navigation/header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {LoginComponent} from './auth/login/login.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./auth/auth.service";
import {XhrInterceptorService} from "./rest/xhr-interceptor.service";
import {CreateAccommodationComponent} from './create-accommodation/create-accommodation.component';
import {MatSelectModule} from "@angular/material/select";
import {RegisterComponent} from './auth/register/register.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {AccommodationDetailComponent} from './accommodation-detail/accommodation-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LayoutComponent,
    HeaderComponent,
    LoginComponent,
    CreateAccommodationComponent,
    RegisterComponent,
    AccommodationDetailComponent
  ],
  imports: [
    BrowserModule,
    RoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FlexLayoutModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [AuthService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptorService, multi: true},
    {provide: APP_INITIALIZER, useFactory: (authService: AuthService) => () => authService.readUser(),
    multi: true, deps: [AuthService]},
    {provide: MAT_DATE_LOCALE, useValue: 'hu-HU'},

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
