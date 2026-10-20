import { Routes } from '@angular/router';
import { MainPage } from './page/mainpage';
import { CategoryPage } from './page/categorypage';
import { ArticlePage } from './page/articlepage';

export const routes: Routes = [
  { path: 'news/:id', component: ArticlePage },
  { path: 'category/:category', component: CategoryPage },
  { path: '', pathMatch: 'full', component: MainPage },
  { path: '**', redirectTo: '' }
];
