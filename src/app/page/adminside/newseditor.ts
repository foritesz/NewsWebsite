import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Article } from '../../model/article';
import { ArticleService } from '../../service/crudservice';

@Component({
  selector: 'app-news-editor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './newseditor.html',
})
export class NewsEditorComponent {
  title = '';
  category = 'Technology';
  introduction = '';
  content = '';
  image = '';
  frontpage = false;
  submittedArticle?: Article;
  readonly categories: string[];
  articles: Article[];

  constructor(private readonly articleService: ArticleService) {
    this.articles = articleService.getAll().map((article, index) => this.normalizeArticle(article, index));
    this.articleService.saveAll(this.articles);
    this.categories = ArticleService.defaultCategories;
  }

  onSubmit(): void {
    const title = this.title.trim();
    const category = this.category.trim();
    const introduction = this.introduction.trim();
    const content = this.content.trim();
    const image = this.image.trim();
    if (!title || !category || !introduction || !content || !image || !this.categories.includes(category)) return;

    const nextId = this.articles.reduce(
      (maximum, article) => Math.max(maximum, article.id),
      0,
    ) + 1;

    const article = this.normalizeArticle({
      id: nextId,
      views: 0,
      title,
      category,
      frontpage: this.frontpage,
      introduction,
      content,
      image,
      date: new Date(),
    }, this.articles.length);

    this.articles.push(article);
    this.articleService.saveAll(this.articles);
    this.submittedArticle = article;

    this.title = '';
    this.category = this.categories[0] ?? '';
    this.introduction = '';
    this.content = '';
    this.image = '';
    this.frontpage = false;
  }

  private normalizeArticle(value: unknown, index: number): Article {
    const record = value && typeof value === 'object'
      ? value as Record<string, unknown>
      : {};
    const id = Number(record['id']);
    const views = Number(record['views']);
    const rawDate = record['date'];
    const parsedDate = rawDate ? new Date(String(rawDate)) : undefined;
    const title = typeof record['title'] === 'string'
      ? record['title'].trim()
      : typeof record['name'] === 'string'
        ? record['name'].trim()
        : '';

    return {
      id: Number.isFinite(id) && id > 0 ? id : index + 1,
      views: Number.isFinite(views) && views >= 0 ? views : 0,
      category: typeof record['category'] === 'string' ? record['category'].trim() : '',
      frontpage: record['frontpage'] === true,
      title,
      introduction: typeof record['introduction'] === 'string' ? record['introduction'].trim() : '',
      content: typeof record['content'] === 'string' ? record['content'].trim() : '',
      image: typeof record['image'] === 'string' ? record['image'].trim() : '',
      date: parsedDate && !Number.isNaN(parsedDate.getTime()) ? parsedDate : new Date(),
    };
  }
}
