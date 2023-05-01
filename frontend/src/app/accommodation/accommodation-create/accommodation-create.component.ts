import {Component, OnInit} from '@angular/core';
import {UntypedFormArray, UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {AccommodationService} from "../../service/accommodation.service";
import {NgxImageCompressService} from "ngx-image-compress";
import {Router} from "@angular/router";
import {Address} from "ngx-google-places-autocomplete/objects/address";

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './accommodation-create.component.html',
  styleUrls: ['./accommodation-create.component.css']
})
export class AccommodationCreateComponent implements OnInit {

  form!: UntypedFormGroup;
  listOfFiles: File[] = [];
  previews: string[] = [];
  listOfDates: Array<{ from: number, end: number, price: number }> = [];
  primaryImageIndex: number | null = null;

  center: google.maps.LatLngLiteral = {lat: 47, lng: 19};
  zoom: number = 7;
  marker: any = null;

  addressOptions: any = {
    types: ['address'],
  }
  googleMapsOptions: google.maps.MapOptions = {
    mapTypeId: 'hybrid',
    disableDoubleClickZoom: true,
    streetViewControl: false,
  };

  address!: Address;

  constructor(private fb: UntypedFormBuilder,
              private imageCompress: NgxImageCompressService,
              private accommodationService: AccommodationService,
              private router: Router) {
  }

  get announceDates() {
    return this.form.controls["announceDates"] as UntypedFormArray;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(6)]],
      address: [null, [Validators.required]],
      floor: [null, []],
      door: [null, []],
      map: [null, [Validators.required]],
      description: [null, [Validators.required]],
      maxGuest: [null, [Validators.required, Validators.max(100), Validators.min(1)]],
      announceDates: this.fb.array([this.fb.group({
        fromDate: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
        price: [null, [Validators.required]],
      })]),
      imageInput: [null, [Validators.required]]
    });
  }

  public addAnnounceDate() {
    const announceDateForm = this.fb.group({
      fromDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      price: [null, [Validators.required]],
    });
    this.announceDates.push(announceDateForm);
  }

  public deleteAnnounceDate(index: number) {
    if (this.announceDates.length > 1)
      this.announceDates.removeAt(index);
  }

  public selectFile(event: any) {
    console.log(event.target.files.length);
    this.listOfFiles.push(event.target.files[0]);
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.previews.push(e.target.result);
    };

    reader.readAsDataURL(event.target.files[0]);
  }

  public removeSelectedFile(index: number) {
    this.listOfFiles.splice(index, 1);
    this.previews.splice(index, 1);
    if (this.primaryImageIndex === index)
      this.primaryImageIndex = null;
    if (this.listOfFiles.length == 0)
      this.form.get("imageInput")?.reset();
  }

  public setPrimaryImage(index: number) {
    this.primaryImageIndex = index;
    console.log(index);
  }

  public dropMarker(event: any) {
    this.marker = {
      position: {
        lat: event.latLng.lat(),
        lng: event.latLng.lng(),
      },
      options: {
        animation: google.maps.Animation.DROP,
      },
    }
    this.form.get("map")?.setValue("asd");
  }

  public handleAddressChange(address: Address) {
    this.address = address;
    if (address.formatted_address !== undefined) {
      console.log(address.formatted_address);
      this.center = {
        lat: address.geometry.location.lat(),
        lng: address.geometry.location.lng(),
      }
      this.zoom = 15;
    }
  }

  async saveAccommodation(form: UntypedFormGroup) {
    const formData = new FormData()
    formData.append('accommodation', JSON.stringify({
      name: form.get("name")?.value,
      address: this.address.formatted_address !== undefined ? this.address.formatted_address : this.address.name,
      floor: form.get("floor")?.value,
      door: form.get("door")?.value,
      lat: this.marker.position.lat,
      lng: this.marker.position.lng,
      description: form.get("description")?.value,
      maxGuests: form.get("maxGuest")?.value,
      announces: this.mapAnnounceDates(),
      mainImageIndex: this.primaryImageIndex
    }))
    this.listOfFiles.forEach(f => formData.append('files', f));
    this.uploadToServer(formData);
  }

  private uploadToServer(formData: FormData) {
    this.accommodationService
      .newAccommodation(formData)
      .subscribe({
        next: (data) => {
          console.log(data)
          alert(data.data);
          this.router.navigateByUrl("/");
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
        }
      });
  }

  private mapAnnounceDates(): Array<string> {
    return this.announceDates.value.map((announceDate: { fromDate: string, endDate: string, price: number }) => {
      return {from: Date.parse(announceDate.fromDate), end: Date.parse(announceDate.endDate), price: announceDate.price}
    })
  }
}
