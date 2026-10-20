import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../../model/article';
import { SortingService } from '../../service/sortingservice';

@Component({
  selector: 'app-weeklynews',
  imports: [RouterLink],
  templateUrl: './weeklynews.html',
  styleUrl: './weeklynews.css',
})
export class WeeklyNews {
  readonly articles: Article[];

  constructor(sortingService: SortingService) {
    this.articles = sortingService.getWeeklyNews();
  }
}
