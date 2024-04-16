package com.pongcase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//BaseEntity는 BaseTimeEntity를 상속 받음
//등록일, 수정일, 등록자, 수정자를 모두 갖는 엔티티는 BaseEntity를 상속받으면 됨

//Entity를 데이터베이스에 적용하기 이전, 이후에 커스텸 콜백을 요청할 수 있는 어노테이션
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy  //데이터 생성자 자동 저장하는 어노테이션
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 데이터 수정자 자동 저장 어노테이션
    private String modifiedBy;

}