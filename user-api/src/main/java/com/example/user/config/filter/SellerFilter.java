package com.example.user.config.filter;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.common.UserVo;
import com.example.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(urlPatterns = "/seller/*")
@RequiredArgsConstructor
public class SellerFilter implements Filter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if (!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("Invalid Access");
        }

        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        sellerService.findByIdAndEmail(userVo.getId(), userVo.getEmail()).orElseThrow(
                () -> new ServletException("Invalid access")
        );

        chain.doFilter(request, response);
    }
}
