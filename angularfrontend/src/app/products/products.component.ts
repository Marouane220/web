import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, Route } from '@angular/router';
import { CatalogueService } from '../catalogue.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products;

  constructor(private catalogueService: CatalogueService,
              public route: ActivatedRoute,
              public router: Router) { }

  ngOnInit() {
    this.router.events.subscribe(value =>{
      if(value instanceof NavigationEnd){
        let url = value.url;
        let p1 = this.route.snapshot.params.p1;
        if(p1 == 1){
          this.getProducts('/products/search/selectedProducts');
        } else if(p1 == 2){
          let idCat = this.route.snapshot.params.p2;
          this.getProducts('/categories/'+idCat+'/products');
        }
      }
    });
    let p1 = this.route.snapshot.params.p1;
    if(p1==1){
      this.getProducts('/products/search/selectedProducts');
    } 
  }

  private getProducts(url){
    this.catalogueService.getResource(url).subscribe(data =>{
      this.products=data;
    }, err => {
      console.log(err);
    })
  }
}
