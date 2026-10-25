package com.example.newsbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="News")
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int Views;
  private Category Category;
  private boolean Frontpage;
  private String Title;
  private String Introduction;
  private String Content;
  private String ImageUrl;
  private Date PublishDate;
  private String Author;



}
