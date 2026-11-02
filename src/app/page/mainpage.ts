import { AsyncPipe } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { NewsFeedService } from '../service/newsfeedservice';
import { AdSpace } from './partofpage/adspace';
import { PopularNews } from './partofpage/popularnews';
import { WeeklyNews } from './partofpage/weeklynews';

@Component({
  selector: 'app-mainpage',
  imports: [WeeklyNews, AdSpace, PopularNews, RouterLink, AsyncPipe],
  templateUrl: './mainpage.html',
  styleUrl: './mainpage.css',
})
export class MainPage {
  readonly mainArticles: Article[];
  readonly freshNews: ReturnType<NewsFeedService['getFreshNews']>;

  constructor(articleService: ArticleService, private readonly newsFeedService: NewsFeedService) {
    this.mainArticles = articleService.getAll().filter(article => article.frontpage);
    this.freshNews = newsFeedService.getFreshNews();
  }
}
