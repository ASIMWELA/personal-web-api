package com.personal.website.service;

import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserNameOrEmail(userNameOrEmail,userNameOrEmail).orElseThrow(
                ()->new EntityNotFoundException("No user with user name "+ userNameOrEmail)
        );

        return UserDetailsImpl.build(user);
    }
}
