import { Injectable } from '@angular/core';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  articles: Article[] = [
    { id: 1, views: 100, category: 'Technology', frontpage: true, title: 'Angular 12 Released', introduction: 'Angular 12 is now available with new features.', content: 'The Angular team has released version 12 of the framework...', image: 'angular12.jpg' , date: new Date() },
    { id: 2, views: 50, category: 'Health', frontpage: false, title: 'Health Benefits of Yoga', introduction: 'Yoga has numerous health benefits.', content: 'Practicing yoga regularly can improve flexibility...', image: 'yoga.jpg', date: new Date() },
    { id: 3, views: 75, category: 'Travel', frontpage: true, title: 'Top 10 Travel Destinations', introduction: 'Discover the top travel destinations for 2022.', content: 'From tropical beaches to historic cities...', image: 'travel.jpg', date: new Date() },
    { id: 4, views: 120, category: 'Technology', frontpage: true, title: 'New Features in React 18', introduction: 'React 18 introduces several exciting new features.', content: 'The latest version of React includes improved performance and new APIs...', image: 'react18.jpg', date: new Date() },
    { id: 5, views: 60, category: 'Lifestyle', frontpage: false, title: 'Healthy Eating Habits', introduction: 'Maintaining a balanced diet is essential for good health.', content: 'Eating a variety of foods can help you get the nutrients your body needs...', image: 'healthy-eating.jpg', date: new Date() },
    { id: 6, views: 90, category: 'Sports', frontpage: true, title: 'Olympic Games Preview', introduction: 'Get ready for the upcoming Olympic Games.', content: 'The Olympic Games are just around the corner, and athletes from around the world are preparing...', image: 'olympics.jpg', date: new Date() }
  ];

  getAll(): Article[] {
    return this.articles;
  }

  create(title: string): void {
    const newArticle: Article = {
      id: Date.now(),
      title: title,
      views: 0,
      category: '',
      frontpage: false,
      introduction: '',
      content: '',
      image: ''
    };

    this.articles.push(newArticle);
  }

  update(id: number, title: string): void {
    const article = this.articles.find(a => a.id === id);

    if (article) {
      article.title = title;
    }
  }

  delete(id: number): void {
    this.articles = this.articles.filter(a => a.id !== id);
  }
}