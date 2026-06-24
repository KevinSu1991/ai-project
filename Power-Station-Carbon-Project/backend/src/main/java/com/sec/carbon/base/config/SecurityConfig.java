package com.sec.carbon.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 配置类
 * <p>注入 BCrypt 密码编码器</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Configuration
public class SecurityConfig {

    /**
     * BCrypt 密码编码器，用于密码加密和校验
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
