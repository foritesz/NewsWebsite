import { Routes } from '@angular/router';
import { MainPage } from './page/mainpage';
import { CategoryPage } from './page/categorypage';
import { ArticlePage } from './page/articlepage';
import { NewsEditorComponent } from './page/adminside/newseditor';
import { LoginComponent } from './page/adminside/login/login';
import { authenticationGuard } from './core/authentication.guard';

export const routes: Routes = [
  { path: 'news/:id', component: ArticlePage },
  { path: 'category/:category', component: CategoryPage },
  { path: 'author/:id', component: NewsEditorComponent, canActivate: [authenticationGuard] },
  { path: 'author', component: NewsEditorComponent, canActivate: [authenticationGuard] },
  { path: 'adminside/login', component: LoginComponent },
  { path: 'login', redirectTo: 'adminside/login', pathMatch: 'full' },
  { path: '', pathMatch: 'full', component: MainPage },
  { path: '**', redirectTo: '' }
];
