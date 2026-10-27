package com.example.newsbackend.repository;

import com.example.newsbackend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer>, NewsStore {
}
