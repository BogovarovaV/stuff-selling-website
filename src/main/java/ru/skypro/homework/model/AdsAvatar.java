package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsAvatar {

    @Id
    private String id;

    @Lob
    private byte[] image;

 //   private Integer adsId;

}
