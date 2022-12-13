package com.example.backend.global.security;

import com.example.backend.global.security.auth.UserDetailsServiceImpl;
import com.example.backend.global.config.jwt.ExceptionHandlerFilter;
import com.example.backend.global.config.jwt.JWTCheckFilter;
import com.example.backend.global.config.jwt.JWTLoginFilter;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // h2 하위 모든 요청과 파비콘은 제한 무시하는 것으로 설정
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error"
        ,"/swagger-ui/**");
    }


//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable();

        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), userRepository);
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), userDetailsServiceImpl);

        // CORS
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("*"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setAllowCredentials(true);
            cors.setExposedHeaders(List.of("*"));
            return cors;
        });
        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();


        http
                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()


                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                .authorizeRequests()

//                .antMatchers(  "/chat/**").permitAll() // swagger
//                .antMatchers(HttpMethod.GET, "/image/**").permitAll()
//                .antMatchers("/chat/**").permitAll()

                .antMatchers("/v1/signup", "/v1/emailconfirm", "/v1/realtor/signup", "/v1/login", "/v1/*", "/awsHealthCheck").permitAll()

                .antMatchers("/v1/user").access("hasRole('ROLE_USER')")
                .antMatchers("/v1/myprofile").access("hasRole('USER') or hasRole('REALTOR')")
                .antMatchers( "/v1/myconsult").access("hasRole('USER')")
                .antMatchers( "/v1/waitcustomer").access("hasRole('REALTOR')")
                .antMatchers( "/v1/replied").access("hasRole('REALTOR')")
//
                .antMatchers( "/v1/consult/{consult_id}/img").access("hasRole('REALTOR')")
                .antMatchers( "/v1/consult/{consult_id}/comment").access("hasRole('REALTOR')")
                .antMatchers(HttpMethod.GET, "/v1/consult/{consult_id}").access("hasRole('USER') or hasRole('REALTOR')")
                .antMatchers(HttpMethod.PUT, "/v1/consult/{consult_id}").access("hasRole('USER')")
                .antMatchers( "/v1/advicerequest").access("hasRole('USER')")
                .antMatchers( "/v1/user/profile").access("hasRole('USER')")
                .antMatchers( "/v1/realtor/profile").access("hasRole('REALTOR')")
                .antMatchers(HttpMethod.GET, "/v1/realtor-approval").access("hasRole('ADMIN') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.PUT, "/v1/realtor-approval").access("hasRole('ADMIN') or hasRole('ADMIN')")
                .antMatchers( "/v1/premises").access("hasRole('USER')")
                .antMatchers( "/v1/premises/advicerequest").access("hasRole('USER')")
                .antMatchers( "/v1/premises/allpost").access("hasRole('USER')")
                .antMatchers(HttpMethod.GET, "/v1/premises/{premises_id}").access("hasRole('USER')")
                .antMatchers(HttpMethod.PUT, "/v1/premises/{premises_id}").access("hasRole('USER')")
                .antMatchers(HttpMethod.DELETE, "/v1/premises/{premises_id}").access("hasRole('USER')")
                .antMatchers( "/v1/premises/{premises_id}?page={page}&size=5").access("hasRole('USER')")
                .antMatchers( "/v1/like/{comment_id}").access("hasRole('USER')")
                .antMatchers( "/v1/survey?page={page}&size=5").access("hasRole('USER')")
                .antMatchers( "/v1/myconsult/search?keyword={keyword}").access("hasRole('USER')")
                .antMatchers( "/v1/premises/advicerequest/search?keyword={keyword}").access("hasRole('USER')")
                .antMatchers( "/v1/waitcustomer/search?keyword={keyword}").access("hasRole('REALTOR')")
                .antMatchers( "/v1/replied/search?keyword={keyword}").access("hasRole('REALTOR')")

                 .anyRequest().authenticated()// 나머지는 전부 인증 필요

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JWTLoginFilter.class);
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("happydaddy")
//                .password("{noop}1234")
//                .roles("USER")
//                .and()
//                .withUser("angrydaddy")
//                .password("{noop}1234")
//                .roles("USER")
//                .and()
//                .withUser("guest")
//                .password("{noop}1234")
//                .roles("GUEST");
//    }
}
