package cn.sust.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()   //自定义登录页面
                .loginPage("/login.html")   //登录页面设置
                //登录访问路径(相当于一个控制器,只不过是框架帮我们完成了逻辑判断,登录的表单数据应该往这个控制器发送)
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/test/index").permitAll()   //登录之后的跳转路径
                .and().authorizeRequests()
                .mvcMatchers("/", "/user/login", "/test/hello").permitAll() //设置哪些路径可以直接访问不需要认证
            //    .mvcMatchers("/test/index").hasAuthority("admin")   //查询的用户有这个权限才能访问
            //    .mvcMatchers("/test/index").hasAnyAuthority("admin,manager")
                .mvcMatchers("/test/index").hasRole("sale")
                .anyRequest().authenticated()  //表示除了上面的路径其他都需要认证
                .and().csrf().disable();  //关闭csrf防护
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
