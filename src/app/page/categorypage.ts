import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { AdSpace } from './partofpage/adspace';
import { PopularNews } from './partofpage/popularnews';
import { WeeklyNews } from './partofpage/weeklynews';

@Component({
  selector: 'app-categorypage',
  imports: [WeeklyNews, AdSpace, PopularNews, RouterLink],
  templateUrl: './categorypage.html',
  styleUrl: '../../../categorylist.css'
})
export class CategoryPage {
  category = '';
  articles: Article[] = [];
  categoryExists = false;
  loading = true;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly articleService: ArticleService
  ) {
    this.route.paramMap.subscribe(params => {
      this.category = params.get('category') ?? '';
      this.articles = [];
      this.categoryExists = false;
      this.loading = true;
      this.articleService.getCategoryNews(this.category).subscribe(articles => {
        this.articles = articles;
        this.categoryExists = articles.length > 0;
        this.loading = false;
      });
    });
  }
}
