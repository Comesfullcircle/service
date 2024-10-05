package org.delivery.storeadmin.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final StoreUserConverter storeUserConverter;

    @GetMapping("/user-profile")
    public String getUserProfile(Model model) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSession userSession = (UserSession) authentication.getPrincipal();

        // UserSession을 StoreUserResponse로 변환
        StoreUserResponse storeUserResponse = storeUserConverter.toResponse(userSession);

        // 모델에 추가하여 Thymeleaf로 전달
        model.addAttribute("storeUserResponse", storeUserResponse);
        return "user-profile"; // "user-profile.html" 템플릿 이름
    }
}
