import { Routes } from '@angular/router';
import { MainPage } from './page/mainpage';
import { CategoryPage } from './page/categorypage';
import { ArticlePage } from './page/articlepage';
import { NewsEditorComponent } from './page/adminside/newseditor';

export const routes: Routes = [
  { path: 'news/:id', component: ArticlePage },
  { path: 'category/:category', component: CategoryPage },
  { path: 'author/:id', component: NewsEditorComponent },
  { path: 'author', component: NewsEditorComponent },
  { path: '', pathMatch: 'full', component: MainPage },
  { path: '**', redirectTo: '' }
];
