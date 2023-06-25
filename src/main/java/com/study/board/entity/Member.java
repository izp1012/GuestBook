package com.study.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//BaseEntity 의 상속을 통해서 생성시간, 수정시간 관리까지 적용
@Table(name ="inhyo_Member")
public class Member  extends BaseEntity{
    @Id
    private String email;

    private String password;

    private String name;
}