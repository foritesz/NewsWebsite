import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../../model/article';
import { SortingService } from '../../service/sortingservice';

@Component({
  selector: 'app-popularnews',
  imports: [RouterLink],
  templateUrl: './popularnews.html',
  styleUrl: './popularnews.css',
})
export class PopularNews {
  readonly articles: Article[];

  constructor(sortingService: SortingService) {
    this.articles = sortingService.getMostPopularNews();
  }
}
