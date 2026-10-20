import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Article } from '../model/article';
import { ArticleService } from '../service/crudservice';
import { PopularNews } from './partofpage/popularnews';

@Component({
  selector: 'app-articlepage',
  imports: [DatePipe, RouterLink, PopularNews],
  templateUrl: './newspage.html',
  styleUrl: './articlepage.css',
})
export class ArticlePage {
  article?: Article;

  constructor(route: ActivatedRoute, articleService: ArticleService) {
    route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.article = Number.isFinite(id) ? articleService.getById(id) : undefined;
    });
  }
}
