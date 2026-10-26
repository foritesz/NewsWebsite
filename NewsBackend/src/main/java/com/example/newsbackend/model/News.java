package com.example.newsbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="\"News\"")
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int views;
  private String category;
  private boolean frontpage;
  private String title;
  private String introduction;
  private String content;
  @Column(name = "image")
  private String imageUrl;

  @Column(name = "date")
  private Date publishDate;

  @Column(name = "id_auth")
  private Integer author;

}
