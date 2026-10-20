import { Injectable } from '@angular/core';
import { Article } from '../model/article';
import { ArticleService } from './crudservice';

@Injectable({
  providedIn: 'root'
})
export class SortingService {
    constructor(private articleService: ArticleService) {}
  getFreshNews(): Article[] {
    const articles = this.articleService.getAll();
    return [...articles]
      .sort((a, b) => (b.date?.getTime() || 0) - (a.date?.getTime() || 0))
      .slice(0, 5);
  }
  getMostPopularNews(): Article[] {
    const articles = this.articleService.getAll();
    return articles
      .filter(article => article.frontpage === false)
      .sort((a, b) => b.views - a.views)
      .slice(0, 5);
  }
  getWeeklyNews(): Article[] {
    const articles = this.articleService.getAll();
    return [...articles]
      .sort((a, b) => b.views - a.views)
      .slice(0, 5);
  }
  getSearchResults(searchTerm: string): Article[] {
    const articles = this.articleService.getAll();
    return articles.filter(article => 
      article.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      article.introduction.toLowerCase().includes(searchTerm.toLowerCase()) ||
      article.content.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
  getCategoryNews(category: string): Article[] {
    const articles = this.articleService.getAll();
    const normalizedCategory = category.trim().toLocaleLowerCase();
    return articles.filter(
      article => article.category.trim().toLocaleLowerCase() === normalizedCategory
    );
  }
}
