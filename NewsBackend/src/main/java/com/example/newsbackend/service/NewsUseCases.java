package com.example.newsbackend.service;

import com.example.newsbackend.domain.Category;
import com.example.newsbackend.entity.NewsEntity;

import java.util.List;

public interface NewsUseCases {
  List<NewsEntity> getWeeklyNews();
  List<NewsEntity> getFreshNews();
  List<NewsEntity> getPopularNews();
  List<NewsEntity> getCategory(Category category);
  NewsEntity addNews(NewsEntity article);
}
