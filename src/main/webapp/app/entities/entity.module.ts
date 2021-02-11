import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'appuser',
        loadChildren: () => import('./appuser/appuser.module').then(m => m.RealEstateAppuserModule),
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.RealEstateCompanyModule),
      },
      {
        path: 'property',
        loadChildren: () => import('./property/property.module').then(m => m.RealEstatePropertyModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.RealEstateLocationModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.RealEstatePhotoModule),
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.RealEstateNotificationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RealEstateEntityModule {}
