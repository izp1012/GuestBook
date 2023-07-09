package com.study.mreview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie") //연관 관계시 항상 주의
@Table(name = "ih_movie_image")
public class MovieImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Movie movie;


}