package com.example.newsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;
  private boolean frontpage;
  @NotNull
  @Column(nullable = false)
  private String title;
  @NotNull
  @Column(nullable = false)
  private String introduction;
  @NotNull
  @Column(nullable = false)
  private String content;
  @Column(name = "image")
  private String imageUrl;

  @NotNull
  @Column(name = "date", nullable = false)
  private Date publishDate;

  @NotNull
  @Column(name = "id_auth", nullable = false)
  private Integer author;

}
