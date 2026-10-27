package com.example.newsbackend.service;

import com.example.newsbackend.model.Category;
import com.example.newsbackend.model.News;

import java.util.List;

public interface NewsUseCases {
  List<News> getWeeklyNews();
  List<News> getFreshNews();
  List<News> getPopularNews();
  List<News> getCategory(Category category);
  News addNews(News article);
}
