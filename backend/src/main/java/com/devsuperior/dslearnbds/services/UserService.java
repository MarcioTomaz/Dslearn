package com.devsuperior.dslearnbds.services;

import com.devsuperior.dslearnbds.DTO.UserDTO;
import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){

        authService.validateSelfOrAdmin(id);

        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));

        return new UserDTO(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(userName);

        if( user == null){
            logger.error("user not found: " + userName);
            throw new UsernameNotFoundException("Email not found");
        }

        logger.info("User found: " + userName);
        return user;
    }
}
