package org.delivery.storeadmin.presentation;

import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {

    @GetMapping(path = {"", "/main"})
    public String main(Model model) {
        // SecurityContextHolder를 통해 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserSession) {
            UserSession userSession = (UserSession) authentication.getPrincipal();

            // 모델에 UserSession 필드 추가하여 Thymeleaf로 전달
            model.addAttribute("userId", userSession.getUserId());
            model.addAttribute("email", userSession.getEmail());
            model.addAttribute("status", userSession.getStatus());
            model.addAttribute("role", userSession.getRole());
            model.addAttribute("registeredAt", userSession.getRegisteredAt());
            model.addAttribute("unregisteredAt", userSession.getUnregisteredAt());
            model.addAttribute("lastLoginAt", userSession.getLastLoginAt());
            model.addAttribute("storeId", userSession.getStoreId());
            model.addAttribute("storeName", userSession.getStoreName());

            System.out.println("Authentication name: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Principal: " + userSession);
        }

        return "main"; // "main.html" 템플릿 이름
    }


    @RequestMapping("/order")
    public ModelAndView order() {
        // SecurityContextHolder를 통해 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Authentication name: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Principal: " + authentication.getPrincipal());
        }

        return new ModelAndView("order/order");
    }
}
