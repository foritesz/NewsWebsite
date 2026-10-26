package com.example.newsbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Name;
    private String Password;
    private String UserName;


}
