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
                , "/error");
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

                .antMatchers("/swagger-ui/**", "/v3/**", "/test").permitAll() // swagger
                .antMatchers(HttpMethod.GET, "/image/**").permitAll()

                .antMatchers("/v1/signup","/v1/emailconfirm","/v1/realtor/signup", "/v1/login").permitAll()

                .antMatchers(HttpMethod.GET, "/v1/myprofile").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/myprofile").access("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_REALTOR')")

                .antMatchers(HttpMethod.GET, "/v1/myconsult").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/myconsult").access("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/v1/waitcustomer").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/waitcustomer").access("hasRole('ROLE_REALTOR')or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/v1/replied").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/replied").access("hasRole('ROLE_REALTOR')or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.DELETE, "/v1/user").authenticated()
                .antMatchers(HttpMethod.DELETE, "/v1/user").access("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_REALTOR')")
                .antMatchers(HttpMethod.PUT, "/v1/user/profile").authenticated()
                .antMatchers(HttpMethod.PUT, "/v1/user/profile").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.PUT, "/v1/realtor/profile").authenticated()
                .antMatchers(HttpMethod.PUT, "/v1/realtor/profile").access("hasRole('ROLE_REALTOR') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/v1/realtor-approval").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/realtor-approval").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PUT, "/v1/realtor-approval").authenticated()
                .antMatchers(HttpMethod.PUT, "/v1/realtor-approval").access("hasRole('ROLE_ADMIN')")


                .antMatchers(HttpMethod.POST, "/v1/consult/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/consult/{consult_id}").access("hasRole('ROLE_REALTOR') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/v1/consult/{consult_id}").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/consult/{consult_id}").access("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_REALTOR')")
                .antMatchers(HttpMethod.POST, "/v1/consult/{consult_id}/img").access("hasRole('ROLE_REALTOR') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/v1/consult/{consult_id}/comment").access("hasRole('ROLE_REALTOR') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PUT, "/v1/consult/{consult_id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/v1/consult/{consult_id}").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.POST, "/v1/advicerequest").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/advicerequest").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/v1/footsteps").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/footsteps").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/v1/premises/**").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/premises/advicerequest").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/v1/premises/allpost").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/v1/premises/{premises_id}").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/v1/premises/{premises_id}?page={page}&size=5").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")


                .anyRequest().hasAnyRole("ROLE_ADMIN")

                // .anyRequest().authenticated() // 나머지는 전부 인증 필요
                // .anyRequest().permitAll()   // 나머지는 모두 그냥 접근 가능

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JWTLoginFilter.class);
    }
}
