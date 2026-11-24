package com.example.demo.Service;

import com.example.demo.Dto.UserRequest;
import com.example.demo.Dto.UserResponse;
import com.example.demo.Entity.User;
import com.example.demo.Exception.EmailException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create
    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByEmail(userRequest.email())){
            throw new EmailException("Email already exists");
        }

        User user = new User();
        user.setEmail(userRequest.email());
        user.setUserName(userRequest.userName());

        User newUser = userRepository.save(user);
        return mapToResponse(newUser);
    }

    //Read All
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //Read one
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){
        User user = findUserOrThrow(id);
        return mapToResponse(user);
    }

    //Update
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest){
        User user = findUserOrThrow(id);
        boolean emailUsedByAnotherUser = userRepository.existsByEmailAndIdNot(userRequest.email(),
        id);

        if(emailUsedByAnotherUser){
            throw new EmailException("Email already exists");
        }

        user.setEmail(userRequest.email());
        user.setUserName(userRequest.userName());

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    //Delete User by Id
    @Transactional
    public void deleteUser(Long id){
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    private User findUserOrThrow(Long id){

        return userRepository
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException(
                        "User no found by ID: " + id
                ));
    }

    private UserResponse mapToResponse(User user){
        return new  UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}
