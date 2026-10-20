import { Routes } from '@angular/router';
import { MainPage } from './page/mainpage';
import { CategoryPage } from './page/categorypage';

export const routes: Routes = [
  { path: 'category/:category', component: CategoryPage },
  { path: '', pathMatch: 'full', component: MainPage },
  { path: '**', redirectTo: '' }
];
