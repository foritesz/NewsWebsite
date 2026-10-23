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
  views: number = 0;
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly articleService = inject(ArticleService);

  constructor() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.article = Number.isFinite(id) ? this.articleService.getById(id) : undefined;
      if (!this.article) return;

      this.views = this.article.views + 1;
      this.articleService.saveAll(this.articleService.getAll().map(article =>
        Number(article.id) === id ? { ...article, views: this.views } : article,
      ));
      this.article = { ...this.article, views: this.views };
    });
  }

  deleteArticle(id: number): void {
    this.articleService.delete(id);
    this.router.navigate(['/']);
  }
}
