package com.study.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//MappedSuperclass 어노테이션이 적용된 클래스는 테이블로 생성되지 않는다
//BaseEntity 클래스를 상속한 엔티티의 클래스로 데이터베이스 테이블이 생성
@MappedSuperclass
/* JPA에서 사용하는 엔티티 객체들은 영속 콘텍스트라는 곳에서 관리되는 객체인데, 이 객체들이 변경되면 결과적으로 데이터베이스에 이를 반영한다.
 *  JPAP에서는 엔티티 객체가 유지되고 필요할 때 꺼내서 재사용하는 방식이라 리스너에서 이 객체의 변화를 감지하고 있다.
 * 여기서 엔티티 객체가 생성/변경 되는 것을 감지하는 역할이 AuditingEntityListener 에서 이루어진다.
 * 이를 활성화 시키기 위해서는 프로젝트 생성 시 존재하는 Application에 @EnableJpaAuditing 설정을 추가해야 한다.
 */
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
//실제 테이블은 BaseEntity 클래스를 상속한 엔티티의 클래스로 DB 테이블이 생성된다.
abstract class BaseEntity {

    //생성시간을 처리한다.
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    //최종 수정 시간을 자동으로 처리한다.
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
