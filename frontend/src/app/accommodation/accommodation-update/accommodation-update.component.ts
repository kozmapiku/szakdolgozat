import {Component, OnInit} from '@angular/core';
import {UntypedFormArray, UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {Address} from "ngx-google-places-autocomplete/objects/address";
import {NgxImageCompressService} from "ngx-image-compress";
import {AccommodationService} from "../../service/accommodation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Accommodation} from "../../model/accommodation.model";

@Component({
  selector: 'app-modify-accommodation',
  templateUrl: './accommodation-update.component.html',
  styleUrls: ['./accommodation-update.component.css']
})
export class AccommodationUpdateComponent implements OnInit {
  form!: UntypedFormGroup;
  listOfFiles: File[] = [];
  previews: string[] = [];
  listOfDates: Array<{ from: number, end: number, price: number }> = [];
  primaryImageIndex: number | null = null;

  center: google.maps.LatLngLiteral = {lat: 47, lng: 19};
  zoom: number = 15;
  marker: any = null;

  addressOptions: any = {
    types: ['address'],
  }
  googleMapsOptions: google.maps.MapOptions = {
    disableDoubleClickZoom: true,
    streetViewControl: false,
  };

  address!: Address;


  id!: string | null;
  accommodation!: Accommodation;
  accommodationImages: Array<object> = [];


  constructor(private fb: UntypedFormBuilder,
              private route: ActivatedRoute,
              private imageCompress: NgxImageCompressService,
              private accommodationService: AccommodationService,
              private router: Router) {
  }

  get announceDates() {
    return this.form.controls["announceDates"] as UntypedFormArray;
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getDetailsFromServer()
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
      })])
    });

  }

  public getDetailsFromServer() {
    this.accommodationService.getDetails(this.id).subscribe({
      next: (data) => {
        console.log(JSON.stringify(data));
        this.accommodation = data.data;
        this.fillUpImages();
        this.center = {lat: this.accommodation.lat, lng: this.accommodation.lng};
        this.form.get("name")?.setValue(this.accommodation.name);
        this.form.get("address")?.setValue(this.accommodation.address);
        this.form.get("floor")?.setValue(this.accommodation.floor);
        this.form.get("door")?.setValue(this.accommodation.door);
        this.form.get("map")?.setValue("asd");
        this.form.get("description")?.setValue(this.accommodation.description);
        this.form.get("maxGuest")?.setValue(this.accommodation.maxGuests);

        this.announceDates.at(0).get("price")?.setValue(this.accommodation.announces[0].price);
        this.announceDates.at(0).get("fromDate")?.setValue(new Date(this.accommodation.announces[0].from));
        this.announceDates.at(0).get("endDate")?.setValue(new Date(this.accommodation.announces[0].end));
        this.accommodation.announces.slice(1).forEach(announce => this.addAnnounceDateFilled(new Date(announce.from), new Date(announce.end), announce.price));
        this.marker = {
          position: {
            lat: this.accommodation.lat,
            lng: this.accommodation.lng,
          },
          options: {
            animation: google.maps.Animation.DROP,
          },
        }
        this.center.lng = this.accommodation.lng;
        this.center.lat = this.accommodation.lat;


        this.accommodation.images.forEach(image => {
          const imageBlob = this.dataURItoBlob(image);

          const f = new File([imageBlob], 'MyFileName.png');
          this.selectFileFill(f);
        });
        this.setPrimaryImage(this.accommodation.mainImageIndex);
        this.address = new Address();
        this.address.name = this.accommodation.address;
        //this.addAnnounceDateFilled("asd", "asd", 1000);
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    });
  }

  dataURItoBlob(dataURI: any) {
    const byteString = window.atob(dataURI);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], {type: 'image/png'});
    return blob;
  }

  public addAnnounceDate() {
    const announceDateForm = this.fb.group({
      fromDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      price: [null, [Validators.required]],
    });
    this.announceDates.push(announceDateForm);
  }

  public addAnnounceDateFilled(from: any, end: any, price: any) {
    const announceDateForm = this.fb.group({
      fromDate: [from, [Validators.required]],
      endDate: [end, [Validators.required]],
      price: [price, [Validators.required]],
    });
    this.announceDates.push(announceDateForm);
  }

  public deleteAnnounceDate(index: number) {
    if (this.announceDates.length > 1)
      this.announceDates.removeAt(index);
  }

  public selectFile(event: any) {
    console.log(event);
    this.listOfFiles.push(event.target.files[0]);
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.previews.push(e.target.result);
    };

    reader.readAsDataURL(event.target.files[0]);
  }

  public selectFileFill(event: File) {
    this.listOfFiles.push(event);
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.previews.push(e.target.result);
    };

    reader.readAsDataURL(event);
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
      id: this.id,
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

  private fillUpImages() {
    this.accommodation.images.forEach(image => {
      this.accommodationImages.push(
        {
          image: 'data:image/jpeg;base64,' + image,
          thumbImage: 'data:image/jpeg;base64,' + image,
          alt: 'Egy kép a szállásról'
        })
    })
  }

  private uploadToServer(formData: FormData) {
    this.accommodationService
      .modifyAccommodation(formData)
      .subscribe({
        next: (data) => {
          console.log(data)
          alert(data.data);
          this.router.navigateByUrl("/");
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
          alert(error.error.error);
        }
      });
  }

  private mapAnnounceDates(): Array<string> {
    return this.announceDates.value.map((announceDate: { fromDate: string, endDate: string, price: number }) => {
      return {from: Date.parse(announceDate.fromDate), end: Date.parse(announceDate.endDate), price: announceDate.price}
    })
  }
}
