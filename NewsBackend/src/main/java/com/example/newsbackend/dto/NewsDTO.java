package com.example.newsbackend.dto;

import com.example.newsbackend.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class NewsDTO {

  private int id;
  private int views;
  private Category category;
  private boolean frontpage;
  private String title;
  private String introduction;
  private String content;
  private String imageUrl;
  private Date publishDate;
  private Integer author;
}
