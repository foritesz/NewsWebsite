import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private readonly platformId = inject(PLATFORM_ID);
  private readonly defaultCategories = ['Technology', 'Health', 'Travel', 'Lifestyle', 'Sports'];

  articles: Article[] = [
    { id: 1, views: 100, category: 'Technology', frontpage: true, title: 'Angular 12 Released', introduction: 'Angular 12 is now available with new features.', content: 'The Angular team has released version 12 of the framework...', image: 'angular12.jpg' , date: new Date() },
    { id: 2, views: 50, category: 'Health', frontpage: false, title: 'Health Benefits of Yoga', introduction: 'Yoga has numerous health benefits.', content: 'Practicing yoga regularly can improve flexibility...', image: 'yoga.jpg', date: new Date() },
    { id: 3, views: 75, category: 'Travel', frontpage: false, title: 'Top 10 Travel Destinations', introduction: 'Discover the top travel destinations for 2022.', content: 'From tropical beaches to historic cities...', image: 'travel.jpg', date: new Date() },
    { id: 4, views: 120, category: 'Technology', frontpage: false, title: 'New Features in React 18', introduction: 'React 18 introduces several exciting new features.', content: 'The latest version of React includes improved performance and new APIs...', image: 'react18.jpg', date: new Date() },
    { id: 5, views: 60, category: 'Lifestyle', frontpage: false, title: 'Healthy Eating Habits', introduction: 'Maintaining a balanced diet is essential for good health.', content: 'Eating a variety of foods can help you get the nutrients your body needs...', image: 'healthy-eating.jpg', date: new Date() },
    { id: 6, views: 90, category: 'Sports', frontpage: false, title: 'Olympic Games Preview', introduction: 'Get ready for the upcoming Olympic Games.', content: 'The Olympic Games are just around the corner, and athletes from around the world are preparing...', image: 'olympics.jpg', date: new Date() }
  ];

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

      const normalizedArticles = parsedArticles
        .map((value, index) => this.normalizeArticle(value, index))
        .filter((article): article is Article => article !== null);

      if (normalizedArticles.length > 0) {
        this.articles = normalizedArticles;
      }
    } catch {
      // Invalid or unavailable browser storage falls back to the built-in list.
    }

    return this.articles;
  }

  getCategories(): string[] {
    const storedCategories = this.getAll()
      .map(article => article.category.trim())
      .filter(Boolean);
    return [...new Set([...this.defaultCategories, ...storedCategories])];
  }

  private normalizeArticle(value: unknown, index: number): Article | null {
    if (!value || typeof value !== 'object') return null;

    const record = value as Record<string, unknown>;
    const title = typeof record['title'] === 'string'
      ? record['title'].trim()
      : typeof record['name'] === 'string'
        ? record['name'].trim()
        : '';
    const category = typeof record['category'] === 'string' ? record['category'].trim() : '';
    if (!title || !category) return null;

    const dateValue = record['date'];
    const parsedDate = dateValue ? new Date(String(dateValue)) : undefined;
    const id = Number(record['id']);
    const views = Number(record['views']);

    return {
      id: Number.isFinite(id) && id > 0 ? id : index + 1,
      title,
      category,
      views: Number.isFinite(views) && views >= 0 ? views : 0,
      frontpage: record['frontpage'] === true,
      introduction: typeof record['introduction'] === 'string' ? record['introduction'] : '',
      content: typeof record['content'] === 'string' ? record['content'] : '',
      image: typeof record['image'] === 'string' ? record['image'] : '',
      date: parsedDate && !Number.isNaN(parsedDate.getTime()) ? parsedDate : undefined,
    };
  }

  getById(id: number): Article | undefined {
    return this.getAll().find(article => article.id === id);
  }

  create(articleData: Pick<Article, 'title' | 'category' | 'frontpage' | 'introduction' | 'content' | 'image'>): Article {
    this.getAll();
    const nextId = this.articles.reduce((maxId, article) => Math.max(maxId, article.id), 0) + 1;
    const newArticle: Article = {
      ...articleData,
      id: nextId,
      views: 0,
      date: new Date(),
    };

    this.articles.push(newArticle);
    this.tostorage();
    return newArticle;
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
