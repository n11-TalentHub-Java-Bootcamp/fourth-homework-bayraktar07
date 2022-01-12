package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.UserMapper;
import com.bayraktar.springboot.dto.UserDTO;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.exception.NotFoundException;
import com.bayraktar.springboot.service.entityservice.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserEntityService userEntityService;

    public List<UserDTO> findAll() {
        return UserMapper.INSTANCE.convertAllUserListToUserDTOList(userEntityService.findAll());
    }

    public UserDTO findByName(String name) {
        Optional<User> optionalUser = userEntityService.findByName(name);
        return UserMapper.INSTANCE.convertUserToUserDTO(doesUserExist(optionalUser));
    }

    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userEntityService.findById(id);
        return UserMapper.INSTANCE.convertUserToUserDTO(doesUserExist(optionalUser));
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userEntityService.save(UserMapper.INSTANCE.convertUserDTOToUser(userDTO));
        return UserMapper.INSTANCE.convertUserToUserDTO(user);
    }

    public void deleteById(Long id) {
        userEntityService.deleteById(id);
    }

    public UserDTO update(UserDTO userDTO) {
        return save(findById(userDTO.getId()));
    }

    private User doesUserExist(Optional<User> optionalUser) {
        return optionalUser.orElseThrow(() -> new NotFoundException("User Not Found"));
    }
}
