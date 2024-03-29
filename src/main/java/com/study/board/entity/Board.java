package com.study.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer") //@ToString 은 항상 exclude
//BaseEntity 의 상속을 통해서 생성시간, 수정시간 관리까지 적용
@Table(name ="inhyo_Board")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  //명시적으로 Lazy 로딩 지정
    private Member writer; //연관관계 지정

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
