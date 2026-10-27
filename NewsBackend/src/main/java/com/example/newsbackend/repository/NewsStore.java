package com.example.newsbackend.repository;

import com.example.newsbackend.model.News;

import java.util.List;

/** Application-level port for the News persistence operations. */
public interface NewsStore {
  List<News> findAll();
  News save(News article);
}
