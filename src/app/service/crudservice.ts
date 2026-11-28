import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private readonly platformId = inject(PLATFORM_ID);
  static readonly defaultCategories = ['Technology', 'Health', 'Travel', 'Lifestyle', 'Sports', 'Politics'];

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
          const rawDate = record?.publishDate ?? (record as Article & { date?: unknown })?.date;
          const parsedDate = rawDate ? new Date(rawDate) : undefined;

          return {
            ...record,
            imageUrl: typeof (record as Article & { image?: unknown })?.imageUrl === 'string'
              ? record.imageUrl
              : typeof (record as Article & { image?: unknown })?.image === 'string'
                ? (record as Article & { image: string }).image
                : '',
            id: Number.isFinite(id) && id > 0 ? id : index + 1,
            title: typeof record?.title === 'string' ? record.title : typeof record?.name === 'string' ? record.name : '',
            publishDate: parsedDate && !Number.isNaN(parsedDate.getTime()) ? parsedDate : new Date(),
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

<<<<<<< Updated upstream
  getById(id: number): Article | undefined {
    return this.getAll().find(article => Number(article.id) === Number(id));
=======
  getCategoryNews(category: string): Observable<Article[]> {
    return this.newsFeedService.getNewsCategory(category).pipe(
      map(articles => articles.map((article, index) => this.normalizeArticle(article, index))),
      catchError(() => of(this.getAll().filter(article =>
        article.category.trim().toLocaleLowerCase() === category.trim().toLocaleLowerCase()
      )))
    );
  }

  getById(id: number): Observable<Article | undefined> {
    return this.newsFeedService.getNewsId(id).pipe(
      map(article => this.normalizeArticle(article, 0)),
      catchError(() => of(this.getAll().find(article => Number(article.id) === Number(id))))
    );
  }

  private normalizeArticle(article: Article, index: number): Article {
    const record = article as Article & { name?: unknown; image?: unknown; date?: unknown };
    const id = Number(record?.id);
    const rawDate = record?.publishDate ?? record?.date;
    const parsedDate = rawDate ? new Date(rawDate) : undefined;

    return {
      ...record,
      imageUrl: typeof record?.imageUrl === 'string'
        ? record.imageUrl
        : typeof record?.image === 'string' ? record.image : '',
      id: Number.isFinite(id) && id > 0 ? id : index + 1,
      title: typeof record?.title === 'string' ? record.title : typeof record?.name === 'string' ? record.name : '',
      publishDate: parsedDate && !Number.isNaN(parsedDate.getTime()) ? parsedDate : new Date(),
    };
>>>>>>> Stashed changes
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
