import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
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
  id: number | null = null;
  submittedArticle?: Article;
  readonly categories: string[];
  articles: Article[];

  constructor(
    private readonly articleService: ArticleService,
    private readonly route: ActivatedRoute,
  ) {
    this.articles = articleService.getAll().map((article, index) => this.normalizeArticle(article, index));
    this.articleService.saveAll(this.articles);
    this.categories = ArticleService.defaultCategories;

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      const id = Number(idParam);
      const article = Number.isFinite(id) ? this.articles.find(item => item.id === id) : undefined;
      if (article) {
        this.id = article.id;
        this.title = article.title;
        this.category = article.category;
        this.introduction = article.introduction;
        this.content = article.content;
        this.image = article.image;
        this.frontpage = article.frontpage;
      }
    }
  }

  onSubmit(): void {
    const title = this.title.trim();
    const category = this.category.trim();
    const introduction = this.introduction.trim();
    const content = this.content.trim();
    const image = this.image.trim();
    if (!title || !category || !introduction || !content || !image || !this.categories.includes(category)) return;

    let article: Article;
    if (this.id !== null) {
      const existingArticle = this.articles.find(item => item.id === this.id);
      if (!existingArticle) return;
      article = {
        ...existingArticle,
        title,
        category,
        introduction,
        content,
        image,
        frontpage: this.frontpage,
      };
      this.articles = this.articles.map(item => item.id === this.id ? article : item);
    } else {
      const nextId = this.articles.reduce(
        (maximum, item) => Math.max(maximum, item.id),
        0,
      ) + 1;

      article = this.normalizeArticle({
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
    }
    this.articleService.saveAll(this.articles);
    this.submittedArticle = article;

    if (this.id !== null) return;

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
