package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsAvatar {

    @Id
    private String id;

    @Lob
    private byte[] image;

}
