package com.nowcoder.community.config;

import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration  //一般重写三个方法：configure
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {

    @Override// 忽略掉对静态资源的拦截
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override //做认证，由于认证这部分功能之前自己实现了，因此Spring Security要绕过这部分内容
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }



    @Override //授权
    protected void configure(HttpSecurity http) throws Exception {
        // 注：可以将动态路径列出来，判断哪些路径是只有管理或者版主能访问的
        http.authorizeRequests()
                .antMatchers( // 需要登录之后才能访问的路径
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                        )
                .hasAnyAuthority(
                        // 登陆后哪些权限可以访问
                        AUTHORITY_USER,
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful")
                .hasAnyAuthority(
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )
                .anyRequest().permitAll() // 除了这些请求其他请求都允许
                .and().csrf().disable();  // 不做csrf的检查

        // 如果没有权限，捕获到异常时候的处理
        http.exceptionHandling()  // 有多种请求，有的请求是普通请求（返回html），有的是异步请求（返回json）
                .authenticationEntryPoint( // 配置没有登录时候如何处理
                    new AuthenticationEntryPoint() {
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                            String xRequestedWith = request.getHeader("x-requested-with"); // 查看请求的方式
                            if("XMLHttpRequest".equals(xRequestedWith)){
                                //异步请求：拼一个json字符串，通过浏览器提示
                                response.setContentType("application/plan;charset=utf-8");
                                PrintWriter writer = response.getWriter();
                                writer.write(CommunityUtil.getJSONString(403,"你还没有登录" ));

                            }else{
                                //普通请求：跳转到登录页面强制登录
                                response.sendRedirect(request.getContextPath() + "/login");
                            }
                        }
                    }
                )
                .accessDeniedHandler( //权限不足时这么处理
                    new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                            String xRequestedWith = request.getHeader("x-requested-with"); // 查看请求的方式
                            if ("XMLHttpRequest".equals(xRequestedWith)) {
                                //异步请求：拼一个json字符串，通过浏览器提示
                                response.setContentType("application/plain;charset=utf-8");
                                PrintWriter writer = response.getWriter();
                                writer.write(CommunityUtil.getJSONString(403, "你没有访问此功能的权限!"));
                            }else{
                                //普通请求：跳转到登录页面强制登录
                                response.sendRedirect(request.getContextPath() + "/denied");
                            }
                        }
                });
        // security 默认会自动拦截名为logout的路径，如果这个功能执行了，自己写得logout就不会执行
        //对logout进行配置，覆盖默认路径
        http.logout().logoutUrl("/securitylogout");
    }
}
