import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ArticleService } from './service/crudservice';

@Component({
  selector: 'app-root',
  imports: [RouterLink, RouterOutlet],
  template: `
    <div class="site-shell">
      <header class="header site-header">
        <a class="logo-link" routerLink="/" aria-label="Hírportál – főoldal">
          <div class="logo">
            <img src="1.png" alt="Hírportál logó" />
          </div>
        </a>

        <nav class="categories site-nav" aria-label="Kategóriák">
          @for (category of categories; track category) {
            <a class="category-link" [routerLink]="['/category', category]">{{ category }}</a>
          }
        </nav>

        <div class="searcharea">
          <input class="searchinput" type="search" placeholder="Keresés..." aria-label="Keresés" />
          <a class="searchicon" routerLink="/" aria-label="Keresés">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
          </a>
        </div>
      </header>

      <section class="_socialmedia" aria-label="Közösségi oldalak">
        <p>Facebook</p>
        <p>Twitter</p>
        <p>Instagram</p>
      </section>

      <router-outlet />

      <footer class="site-footer">
        <a class="footer-brand" routerLink="/">Hírportál</a>
        <p>Hírek és történetek naprakészen.</p>
        <nav class="social-links" aria-label="Közösségi oldalak">
          <a href="https://www.facebook.com/" target="_blank" rel="noopener noreferrer">Facebook</a>
          <a href="https://x.com/" target="_blank" rel="noopener noreferrer">Twitter</a>
          <a href="https://www.instagram.com/" target="_blank" rel="noopener noreferrer">Instagram</a>
        </nav>
      </footer>
    </div>
  `
})
export class App {
  readonly categories: string[];

  constructor(articleService: ArticleService) {
    this.categories = articleService.getCategories();
  }
}
