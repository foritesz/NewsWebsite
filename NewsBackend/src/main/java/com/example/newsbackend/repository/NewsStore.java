package com.example.newsbackend.repository;

import com.example.newsbackend.entity.NewsEntity;

import java.util.List;
import java.util.Optional;

/** Application-level port for the News persistence operations. */
public interface NewsStore {
  List<NewsEntity> findAll();
  Optional<NewsEntity> findById(Integer id);
  NewsEntity save(NewsEntity article);
}
