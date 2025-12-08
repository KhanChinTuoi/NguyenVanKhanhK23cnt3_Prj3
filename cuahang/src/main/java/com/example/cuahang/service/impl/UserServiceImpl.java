package com.example.cuahang.service.impl;

import com.example.cuahang.entity.Role;
import com.example.cuahang.entity.User;
import com.example.cuahang.repository.RoleRepository;
import com.example.cuahang.repository.UserRepository;
import com.example.cuahang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User register(User user) {

        // mã hóa mật khẩu
        user.setPassword(encoder.encode(user.getPassword()));

        // gán quyền mặc định: khách hàng
        Role roleKH = roleRepo.findById(3L).orElse(null);
        user.setRole(Set.of(roleKH).toString());

        return userRepo.save(user);
    }
}
