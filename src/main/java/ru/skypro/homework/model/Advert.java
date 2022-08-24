package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte [] image;
    private String description;

    @ManyToOne
    private Users users;
    //    private Integer author;

    @OneToMany
    private List<Comment> commentList;

}
