import { Component } from '@angular/core';
import { Article } from '../../model/article';
import { SortingService } from '../../service/sortingservice';

@Component({
  selector: 'app-weeklynews',
  templateUrl: './weeklynews.html'
})
export class WeeklyNews {
  readonly articles: Article[];

  constructor(sortingService: SortingService) {
    this.articles = sortingService.getWeeklyNews();
  }
}
