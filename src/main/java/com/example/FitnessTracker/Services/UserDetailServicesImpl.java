package com.example.FitnessTracker.Services;

import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServicesImpl implements UserDetailsService {


        @Autowired
        private RegisterRepository registerRepository;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user =  registerRepository.findByUsername(username);
            if(user != null){
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .build();

                return userDetails;
            }
            throw  new UsernameNotFoundException("User not found with username: " + username);
        }


}
