import { Component } from '@angular/core';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { SortingService } from '../service/sortingservice';
import { AdSpace } from './partofpage/adspace';
import { WeeklyNews } from './partofpage/weeklynews';

@Component({
  selector: 'app-mainpage',
  imports: [WeeklyNews, AdSpace],
  templateUrl: './mainpage.html',
})
export class MainPage {
  readonly mainArticles: Article[];
  readonly freshNews: Article[];
  readonly mostPopularNews: Article[];

  constructor(articleService: ArticleService, sortingService: SortingService) {
    this.mainArticles = articleService.getAll().filter(article => article.frontpage);
    this.freshNews = sortingService.getFreshNews();
    this.mostPopularNews = sortingService.getMostPopularNews();
  }
}
