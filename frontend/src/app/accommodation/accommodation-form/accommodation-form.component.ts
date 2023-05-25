import {Component, OnInit} from '@angular/core';
import {FormArray, FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {Address} from "ngx-google-places-autocomplete/objects/address";
import {NgxImageCompressService} from "ngx-image-compress";
import {AccommodationService} from "../../service/accommodation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Accommodation} from "../../model/accommodation.model";
import {AnnounceDate} from "../../model/announce_date.model";

@Component({
  selector: 'app-accommodation-form',
  templateUrl: './accommodation-form.component.html',
  styleUrls: ['./accommodation-form.component.css']
})
export class AccommodationFormComponent implements OnInit {

  form: FormGroup = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      address: [null, [Validators.required, Validators.maxLength(100)]],
      floor: [null, [Validators.min(-10), Validators.max(200)]],
      door: [null, [Validators.min(0), Validators.max(200)]],
      map: [null, [Validators.required]],
      description: [null, [Validators.required, Validators.minLength(100), Validators.maxLength(2500)]],
      maxGuest: [null, [Validators.required, Validators.max(100), Validators.min(1)]],
      announceDates: this.fb.array([this.fb.group({
          fromDate: [null, [Validators.required]],
          endDate: [null, [Validators.required]],
          price: [null, [Validators.required, Validators.min(1), Validators.max(1000000)]],
      })]),
      imageInput: []
  });

    listOfFiles: File[] = [];
    previews: string[] = [];
    listOfDates: Array<{ from: number, end: number, price: number }> = [];
    primaryImageIndex: number | null = null;
    primaryImage: File | null = null;

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

    id!: string | null;
    accommodation!: Accommodation;
    accommodationImages: Array<object> = [];

    constructor(private fb: UntypedFormBuilder,
                private imageCompress: NgxImageCompressService,
                private accommodationService: AccommodationService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    get announceDates() {
        return this.form.controls["announceDates"] as FormArray;
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id');
        if (this.id != null) {
            this.getDetailsFromServer()
        }
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
          this.announceDates.at(0).get("fromDate")?.setValue(new Date(this.accommodation.announces[0].startDate));
          this.announceDates.at(0).get("endDate")?.setValue(new Date(this.accommodation.announces[0].endDate));
          this.accommodation.announces.slice(1).forEach(announce => this.addAnnounceDateFilled(new Date(announce.startDate), new Date(announce.endDate), announce.price));
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
                this.setPrimaryImage(0);
                this.address = new Address();
                this.address.name = this.accommodation.address
                this.form.get("imageInput")?.setValue("asd");
                //this.addAnnounceDateFilled("asd", "asd", 1000);
            },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    });
  }

  public addAnnounceDateFilled(from: any, end: any, price: any) {
    const announceDateForm = this.fb.group({
        fromDate: [from, [Validators.required]],
        endDate: [end, [Validators.required]],
        price: [price, [Validators.required, Validators.min(1), Validators.max(1000000)]],
    });
    this.announceDates.push(announceDateForm);
  }

  dataURItoBlob(dataURI: any) {
    const byteString = window.atob(dataURI);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    return new Blob([int8Array], {type: 'image/png'});
  }

  public selectFileFill(event: File) {
    this.listOfFiles.push(event);
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.previews.push(e.target.result);
    };

    reader.readAsDataURL(event);
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
      if (this.primaryImageIndex === index) {
          this.primaryImageIndex = null;
          this.primaryImage = null;
      }

      if (this.listOfFiles.length == 0)
          this.form.controls["imageInput"].reset();
  }

  public setPrimaryImage(index: number) {
      this.primaryImageIndex = index;
      this.primaryImage = this.listOfFiles[index];
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
    this.form.controls["map"].setValue("placeholder");
  }

  public handleAddressChange(address: Address) {
    this.address = address;
    if (address.formatted_address !== undefined) {
        console.log(address.formatted_address);
        this.center = {
            lat: address.geometry.location.lat(),
            lng: address.geometry.location.lng(),
        }
        this.zoom = 17;
    }
  }

  async saveAccommodation(form: FormGroup) {
    const formData = new FormData()
    formData.append('accommodation', JSON.stringify({
        name: form.controls["name"].value,
        address: this.address.formatted_address !== undefined ? this.address.formatted_address : this.address.name,
        floor: form.controls["floor"].value,
        door: form.controls["door"].value,
        lat: this.marker.position.lat,
        lng: this.marker.position.lng,
        description: form.controls["description"].value,
        maxGuests: form.controls["maxGuest"].value,
        announces: this.mapAnnounceDates()
    }))
      console.log(this.primaryImage)
      if (this.primaryImage != null) {
          formData.append('files', this.primaryImage)
          for (let i = 1; i < this.listOfFiles.length; i++) {
              formData.append('files', this.listOfFiles[i]);
          }
      } else {
          this.listOfFiles.forEach(f => formData.append('files', f));
      }
      this.uploadToServer(formData);
  }

  hasImage() {
    return this.listOfFiles.length > 0;
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
      console.log("id" + this.id);
      if (this.id === null || this.id === undefined) {
          this.accommodationService.newAccommodation(formData)
              .subscribe({
                  next: (data) => {
                      alert(data.data);
                      this.router.navigateByUrl("/");
                  },
                  error: (error) => {
                      alert(error.error.error)
                      console.log("Error " + JSON.stringify(error));
                  }
              });
      } else {
          this.accommodationService.modifyAccommodation(formData)
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

  }

  private mapAnnounceDates(): Array<AnnounceDate> {
    return this.announceDates.value.map((announceDate: { fromDate: string, endDate: string, price: number }) => {
        return {
            startDate: Date.parse(announceDate.fromDate),
            endDate: Date.parse(announceDate.endDate),
            price: announceDate.price
        }
    })
  }
}
