import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { GnListComponent } from './pages/gn/gn';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'gn', component: GnListComponent }
];
