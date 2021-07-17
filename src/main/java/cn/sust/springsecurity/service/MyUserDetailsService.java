package cn.sust.springsecurity.service;


import cn.sust.springsecurity.mapper.UsersMapper;
import cn.sust.springsecurity.pojo.Users;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在数据库中查询用户名密码
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);

        Users users = usersMapper.selectOne(wrapper);
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        System.out.println(users);
        List<GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("manager,ROLE_sale");
        return new User(users.getUsername(),
                new BCryptPasswordEncoder().encode(users.getPassword()),
                authorities);
    }
}
