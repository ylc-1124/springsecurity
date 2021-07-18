package cn.sust.springsecurity.controller;

import cn.sust.springsecurity.pojo.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello security";
    }

    @GetMapping("/index")
    public String index() {
        return "hello index";
    }

    @GetMapping("/update")
    //@Secured({"ROLE_manager","ROLE_sale"})
    //@PreAuthorize("hasAuthority('admin')")
    @PostAuthorize("hasAuthority('update')")
    public String update() {
        System.out.println("update...");
        return "hello update";
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('admin')")
    @PostFilter("filterObject.username=='zhangsan'")  //过滤返回值
    public List<Users> getAllUser() {
        List<Users> list = new ArrayList<>();
        list.add(new Users(1, "zhangsan", "123"));
        list.add(new Users(2, "lisi", "123"));
        return list;
    }
}
