import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { SortingService } from '../service/sortingservice';
import { AdSpace } from './partofpage/adspace';
import { PopularNews } from './partofpage/popularnews';
import { WeeklyNews } from './partofpage/weeklynews';

@Component({
  selector: 'app-mainpage',
  imports: [WeeklyNews, AdSpace, PopularNews, RouterLink],
  templateUrl: './mainpage.html',
  styleUrl: './mainpage.css',
})
export class MainPage {
  readonly mainArticles: Article[];
  readonly freshNews: Article[];

  constructor(articleService: ArticleService, sortingService: SortingService) {
    this.mainArticles = articleService.getAll().filter(article => article.frontpage);
    this.freshNews = sortingService.getFreshNews();
  }
}
