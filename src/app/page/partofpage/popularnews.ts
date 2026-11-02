import { AsyncPipe } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../../model/article';
import { NewsFeedService } from '../../service/newsfeedservice';

@Component({
  selector: 'app-popularnews',
  imports: [RouterLink, AsyncPipe],
  templateUrl: './popularnews.html',
  styleUrl: './popularnews.css',
})
export class PopularNews {
  readonly articles: ReturnType<NewsFeedService['getPopularNews']>;

  constructor(newsFeedService: NewsFeedService) {
    this.articles = newsFeedService.getPopularNews();
  }
}
