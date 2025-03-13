package com.jeong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // BLOB 타입 mapping
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imageDate;

    public ImageEntity(String name, byte[] imageData) {
        this.name = name;
        this.imageDate = imageData;
    }

}
