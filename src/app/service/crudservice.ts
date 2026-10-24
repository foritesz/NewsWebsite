import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private readonly platformId = inject(PLATFORM_ID);
  static readonly defaultCategories = ['Technology', 'Health', 'Travel', 'Lifestyle', 'Sports'];

  articles: Article[] = [];

  tostorage() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('articles', JSON.stringify(this.articles));
    }
  }

  getAll(): Article[] {
    if (!isPlatformBrowser(this.platformId)) {
      return this.articles;
    }

    try {
      const storedArticles = localStorage.getItem('articles');
      if (!storedArticles) return this.articles;

      const parsedArticles: unknown = JSON.parse(storedArticles);
      if (!Array.isArray(parsedArticles) || parsedArticles.length === 0) return this.articles;

      if (parsedArticles.length > 0) {
        this.articles = (parsedArticles as Article[]).map((article, index) => {
          const record = article as Article & { name?: unknown };
          const id = Number(record?.id);
          const rawDate = record?.date;
          const parsedDate = rawDate ? new Date(rawDate) : undefined;

          return {
            ...record,
            id: Number.isFinite(id) && id > 0 ? id : index + 1,
            title: typeof record?.title === 'string' ? record.title : typeof record?.name === 'string' ? record.name : '',
            date: parsedDate && !Number.isNaN(parsedDate.getTime()) ? parsedDate : new Date(),
          };
        });
      }
    } catch {
      // Invalid or unavailable browser storage falls back to the built-in list.
    }

    return this.articles;
  }

  getCategories(): string[] {
    const storedCategories = this.getAll()
      .map(article => typeof article.category === 'string' ? article.category.trim() : '')
      .filter(Boolean);
    return [...new Set([...ArticleService.defaultCategories, ...storedCategories])];
  }

  getById(id: number): Article | undefined {
    return this.getAll().find(article => Number(article.id) === Number(id));
  }

  saveAll(articles: Article[]): void {
    this.articles = articles;
    this.tostorage();
  }

  update(id: number, title: string): void {
    this.getAll();
    const article = this.articles.find(a => a.id === id);

    if (article) {
      article.title = title;
      this.tostorage();
    }
  }

  delete(id: number): void {
    this.getAll();
    this.articles = this.articles.filter(a => a.id !== id);
    this.tostorage();
  }
}
