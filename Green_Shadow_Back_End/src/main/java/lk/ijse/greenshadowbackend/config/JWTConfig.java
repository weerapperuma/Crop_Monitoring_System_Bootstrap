package lk.ijse.greenshadowbackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.greenshadowbackend.service.JWTService;
import lk.ijse.greenshadowbackend.service.UserBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JWTConfig extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UserBo userBo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String initToken = request.getHeader("Authorization");
        String userEmail;
        String jwtToken;

        //Initial Validation
        if (StringUtils.isEmpty(initToken) || !initToken.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Token Received
        jwtToken = initToken.substring(7);
        userEmail = jwtService.extractUsername(jwtToken);

        //User Email Validation
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            //Load User details based on the email
            var loadedUser = userBo.userDetailsService().loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwtToken,loadedUser)){
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(loadedUser,null, loadedUser.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                emptyContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(emptyContext);
            }
        }
        filterChain.doFilter(request,response);
    }

}
