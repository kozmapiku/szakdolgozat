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
import {
  DefaultMatCalendarRangeStrategy,
  MAT_DATE_RANGE_SELECTION_STRATEGY,
  MatDatepickerModule
} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {AccommodationDetailComponent} from './accommodation-detail/accommodation-detail.component';
import {ReservationDialogComponent} from './accommodation-detail/reservation-dialog/reservation-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatMenuModule} from "@angular/material/menu";
import {ProfileComponent} from './profile/profile.component';
import {MyAccommodationsComponent} from './my-accommodations/my-accommodations.component';
import {MyReviewsComponent} from './my-reviews/my-reviews.component';
import {MyReservationsComponent} from './my-reservations/my-reservations.component';
import {NgImageSliderModule} from "ng-image-slider";
import {ReservationDetailsComponent} from './reservation-details/reservation-details.component';
import {ReviewDialogComponent} from './reservation-details/review-dialog/review-dialog.component';
import {StarRatingModule} from "angular-star-rating";
import {GoogleMapsModule} from "@angular/google-maps";
import {GooglePlaceModule} from "ngx-google-places-autocomplete";
import {MapSearchComponent} from './map-search/map-search.component';
import {ModifyAccommodationComponent} from './modify-accommodation/modify-accommodation.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LayoutComponent,
    HeaderComponent,
    LoginComponent,
    CreateAccommodationComponent,
    RegisterComponent,
    AccommodationDetailComponent,
    ReservationDialogComponent,
    ProfileComponent,
    MyAccommodationsComponent,
    MyReviewsComponent,
    MyReservationsComponent,
    ReservationDetailsComponent,
    ReviewDialogComponent,
    MapSearchComponent,
    ModifyAccommodationComponent
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
    MatNativeDateModule,
    MatDialogModule,
    MatMenuModule,
    NgImageSliderModule,
    StarRatingModule.forRoot(),
    GoogleMapsModule,
    GooglePlaceModule
  ],
  providers: [AuthService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptorService, multi: true},
    {
      provide: APP_INITIALIZER, useFactory: (authService: AuthService) => () => authService.readUser(),
      multi: true, deps: [AuthService]
    },
    {provide: MAT_DATE_LOCALE, useValue: 'hu-HU'},
    {
      provide: MAT_DATE_RANGE_SELECTION_STRATEGY,
      useClass: DefaultMatCalendarRangeStrategy,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
