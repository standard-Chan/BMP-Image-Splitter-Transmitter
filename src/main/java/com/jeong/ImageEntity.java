package com.jeong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // BLOB 타입 mapping
    @Lob
    private byte[] imageDate;

    public ImageEntity(String name, byte[] imageData) {
        this.name = name;
        this.imageDate = imageData;
    }

}
