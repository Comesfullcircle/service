package org.delivery.storeadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreAdminApplication.class, args);
    }

    /*
     * Spring Security
     * : 스프링 기반의 어플리케이션 에서의 인증과 권한부여를 구현해둔 보안 프레임워크
     *
     * 주요기증
     * 1. 인증: 사용자가 자신의 신원을 증명하고 로그인 할 수 있도록 제공
     * 2. 권한부여: 인증된 사용자에게 특정 작업 또는 리소스에 접근 권한을 부여
     * 3. 세션관리: 사용자의 세션을 관리하고, 세션 유지 및 만료 시간을 설정
     * 4. 보안설정: 보안 관련 구성을 통하여, URL 또는 리소스에 대한 보안 설정
     * 5. 보안이벤트처리: 인증 및 권한 에러에 대한 이벤트 핸들링을 제공
     */
}
