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
export class AuthorsComponent {
  title = '';
  category = '';
  introduction = '';
  content = '';
  image = '';
  frontpage = false;
  submittedArticle?: Article;
  readonly categories: string[];

  constructor(private readonly articleService: ArticleService) {
    this.categories = articleService.getCategories();
  }

  onSubmit(): void {
    const title = this.title.trim();
    const introduction = this.introduction.trim();
    const content = this.content.trim();
    if (!title || !this.category || !introduction || !content) return;

    this.submittedArticle = this.articleService.create({
      title,
      category: this.category,
      frontpage: this.frontpage,
      introduction,
      content,
      image: this.image.trim(),
    });

    this.title = '';
    this.category = '';
    this.introduction = '';
    this.content = '';
    this.image = '';
    this.frontpage = false;
  }
}
