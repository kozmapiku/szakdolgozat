import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {RoutingModule} from './routing/routing.module';
import {AccommodationSearchComponent} from './accommodation/accommodation-search/accommodation-search.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import {MatSidenavModule} from "@angular/material/sidenav";
import {HeaderComponent} from './navigation/header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {LoginComponent} from './authorization/login/login.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {XhrInterceptorService} from "./rest/xhr-interceptor.service";
import {AccommodationCreateComponent} from './accommodation/accommodation-create/accommodation-create.component';
import {MatSelectModule} from "@angular/material/select";
import {RegisterComponent} from './authorization/register/register.component';
import {
	DefaultMatCalendarRangeStrategy,
	MAT_DATE_RANGE_SELECTION_STRATEGY,
	MatDatepickerModule
} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {AccommodationDetailComponent} from './accommodation/accommodation-detail/accommodation-detail.component';
import {ReservationDialogComponent} from './reservation/reservation-dialog/reservation-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatMenuModule} from "@angular/material/menu";
import {ProfileComponent} from './authorization/profile/profile.component';
import {AccommodationOwnComponent} from './accommodation/accommodation-own/accommodation-own.component';
import {ReviewOwnComponent} from './review/review-own/review-own.component';
import {ReservationOwnComponent} from './reservation/reservation-own/reservation-own.component';
import {NgImageSliderModule} from "ng-image-slider";
import {ReservationDetailsComponent} from './reservation/reservation-details/reservation-details.component';
import {ReviewDialogComponent} from './review/review-dialog/review-dialog.component';
import {StarRatingModule} from "angular-star-rating";
import {GoogleMapsModule} from "@angular/google-maps";
import {GooglePlaceModule} from "ngx-google-places-autocomplete";
import {AccommodationMapSearch} from './accommodation/accommodation-map-search/accommodation-map-search';
import {AccommodationUpdateComponent} from './accommodation/accommodation-update/accommodation-update.component';
import {LayoutComponent} from "./layout/layout.component";
import {
	AccommodationFilterFormComponent
} from './accommodation/accommodation-filter-form/accommodation-filter-form.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {AccommodationFormComponent} from './accommodation/accommodation-form/accommodation-form.component';

@NgModule({
	declarations: [
		AppComponent,
		AccommodationSearchComponent,
		HeaderComponent,
		LoginComponent,
		AccommodationCreateComponent,
		RegisterComponent,
		AccommodationDetailComponent,
		ReservationDialogComponent,
		ProfileComponent,
		AccommodationOwnComponent,
		ReviewOwnComponent,
		ReservationOwnComponent,
		ReservationDetailsComponent,
		ReviewDialogComponent,
		AccommodationMapSearch,
		AccommodationUpdateComponent,
		LayoutComponent,
		AccommodationFilterFormComponent,
		AccommodationFormComponent
	],
	imports: [
		BrowserModule,
		RoutingModule,
		BrowserAnimationsModule,
		CommonModule,
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
		GooglePlaceModule,
		MatCheckboxModule
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
export class AppModule {
}
