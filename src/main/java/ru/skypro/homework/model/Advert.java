package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    private Integer pk;

    private Integer price;
    private String title;
    private String image;
    private String description;

    @ManyToOne
    private Users users;
    //    private Integer author;

    @ManyToMany
    private List<Comment> commentList;



}
