package com.pongcase.config;

/*
    Auditing : Spring Data JPA에서 제공하는 기능,
            엔티티가 저장 또는 수정될 때 자동으로 등록일, 수정일, 등록자, 수정자를
            입력 해 줌으로서 엔티티의 생성과 수정을 감시하고 있음
            해당 데이터를 보고 있다가 생성 또는 수정이 발생하면 자동으로 값을 넣어주는 기능
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration   //Auditing 기능을 사용하기 위해서 새성
@EnableJpaAuditing   //JPA Auditing 기능을 활성화 함
public class AuditConfig  {

    //등록자와 수정자를 처리해주는 AuditorAware을 빈으로 등록
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
