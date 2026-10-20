import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from '../model/article';
import { SortingService } from '../service/sortingservice';
import { AdSpace } from './partofpage/adspace';
import { WeeklyNews } from './partofpage/weeklynews';

@Component({
  selector: 'app-categorypage',
  imports: [WeeklyNews, AdSpace],
  templateUrl: './categorypage.html',
  styleUrl: '../../../categorylist.css'
})
export class CategoryPage {
  category = '';
  articles: Article[] = [];
  categoryExists = false;
  readonly mostPopularNews: Article[];

  constructor(
    private readonly route: ActivatedRoute,
    sortingService: SortingService
  ) {
    this.mostPopularNews = sortingService.getMostPopularNews();
    this.route.paramMap.subscribe(params => {
      this.category = params.get('category') ?? '';
      this.articles = sortingService.getCategoryNews(this.category);
      this.categoryExists = this.articles.length > 0;
    });
  }
}
