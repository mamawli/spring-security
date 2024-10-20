package org.spring.basicauthentication.service;

import lombok.RequiredArgsConstructor;
import org.spring.basicauthentication.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
}
