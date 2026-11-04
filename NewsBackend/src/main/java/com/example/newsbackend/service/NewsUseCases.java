package com.example.newsbackend.service;

import com.example.newsbackend.domain.Category;
import com.example.newsbackend.entity.NewsEntity;

import java.util.List;
import java.util.Optional;

public interface NewsUseCases {
  Optional<NewsEntity> getNewsById(int id);
  boolean setFrontPage(int id, boolean frontpage);
  List<NewsEntity> getWeeklyNews();
  List<NewsEntity> getFreshNews();
  List<NewsEntity> getPopularNews();
  List<NewsEntity> getCategory(Category category);
  NewsEntity addNews(NewsEntity article);
}
