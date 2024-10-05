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

    @RequestMapping(path = {"", "/main"})
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("main");
        // SecurityContextHolder를 통해 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserSession) {
            UserSession userSession = (UserSession) authentication.getPrincipal();

            // ModelAndView에 UserSession 필드 추가
            modelAndView.addObject("userId", userSession.getUserId());
            modelAndView.addObject("email", userSession.getEmail());
            modelAndView.addObject("status", userSession.getStatus());
            modelAndView.addObject("role", userSession.getRole());
            modelAndView.addObject("registeredAt", userSession.getRegisteredAt());
            modelAndView.addObject("unregisteredAt", userSession.getUnregisteredAt());
            modelAndView.addObject("lastLoginAt", userSession.getLastLoginAt());
            modelAndView.addObject("storeId", userSession.getStoreId());
            modelAndView.addObject("storeName", userSession.getStoreName());

            System.out.println("Authentication name: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Principal: " + userSession);
        }

        return modelAndView; // ModelAndView를 반환하여 "main.html"로 이동
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
