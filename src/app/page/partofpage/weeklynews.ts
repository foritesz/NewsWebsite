import { AsyncPipe } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../../model/article';
import { NewsFeedService } from '../../service/newsfeedservice';

@Component({
  selector: 'app-weeklynews',
  imports: [RouterLink, AsyncPipe],
  templateUrl: './weeklynews.html',
  styleUrl: './weeklynews.css',
})
export class WeeklyNews {
  readonly articles: ReturnType<NewsFeedService['getWeeklyNews']>;

  constructor(newsFeedService: NewsFeedService) {
    this.articles = newsFeedService.getWeeklyNews();
  }
}
