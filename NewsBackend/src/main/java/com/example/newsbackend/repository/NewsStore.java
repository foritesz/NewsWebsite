package com.example.newsbackend.repository;

import com.example.newsbackend.entity.NewsEntity;

import java.util.List;

/** Application-level port for the News persistence operations. */
public interface NewsStore {
  List<NewsEntity> findAll();
  NewsEntity save(NewsEntity article);
}
