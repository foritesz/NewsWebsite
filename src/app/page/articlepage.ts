import { Component, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { PopularNews } from './partofpage/popularnews';

@Component({
  selector: 'app-articlepage',
  imports: [DatePipe, RouterLink, PopularNews],
  templateUrl: './articlepage.html',
  styleUrl: './articlepage.css',
})
export class ArticlePage {
  article?: Article;
  loading = true;
  views: number = 0;
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly articleService = inject(ArticleService);
  protected readonly isLoggedIn:boolean=true;// This should be replaced with actual authentication logic

  constructor() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.article = undefined;
      this.loading = true;
      if (!Number.isFinite(id) || id <= 0) {
        this.loading = false;
        return;
      }

      this.articleService.getById(id).subscribe(article => {
        this.article = article;
        this.loading = false;
        if (!article) return;

        this.views = article.views + 1;
        this.article = { ...article, views: this.views };
      });
    });
  }

  deleteArticle(id: number): void {
    this.articleService.delete(id);
    this.router.navigate(['/']);
  }

  updateArticle(id: number): void {
    this.router.navigate(['/author',id]);
  }
}
