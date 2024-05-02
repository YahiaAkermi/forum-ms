package com.yahia.forum.service.impl;

import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.User;
import com.yahia.forum.exception.ResourceNotFoundException;
import com.yahia.forum.exception.UserAlreadyExistsExeption;
import com.yahia.forum.mapper.UserMapper;
import com.yahia.forum.repository.PostsRepository;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service @AllArgsConstructor
public class UserServiceImpl implements IUserService {


    private final UserRepository userRepository;

    private final PostsRepository postsRepository;


    /**
     *this method will create post creator
     *@param userDto -  UserDto  object
     */
    @Override
    public void createUser(UserDto userDto) {

        //transform userDto to user, so I can save it the db
        User user= UserMapper.mapToUser(userDto,new User());

        //I need first to check if the user already exists
        Optional<User> checkedUser=userRepository.findUserByEmail(user.getEmail());



        if(checkedUser.isPresent()){
            throw new UserAlreadyExistsExeption("user already registered with the given email :"+userDto.getEmail());
        }

        //checking if the username is already taken
        boolean usernameAlreadyExists=userRepository.existsUserByUsername(user.getUsername());

        if(usernameAlreadyExists){
            throw new UserAlreadyExistsExeption("username : "+userDto.getUsername()+" is already taken please try another one ");
        }


        //initially he has no posts
        user.setPosts(null);



        //save user to db
        userRepository.save(user);

    }


    /**
     * Fetches account details based on a given mobile number.
     *
     * @param email - Input Email Address
     * @return postCreator  Details based on given email
     */
    @Override
    public UserDto fetchUser(String email) {
            User user= userRepository.findUserByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("PostCreator","email",email)
            );

            UserDto userDto= UserMapper.mapToUserDTo(user,new UserDto());

            return userDto;

    }

    /**
     * Fetches account details based on a given mobile number.
     *
     * @param userDto - Input post creator
     * @return boolean indicating if updating postCreator details is successfull or not
     */
    @Override
    public boolean updateUser(UserDto userDto) {


        //checking first if the user exists using his email ,so I cannot change his email
        User user= userRepository.findUserByEmail(userDto.getEmail()).orElseThrow(
                ()-> new ResourceNotFoundException("PostCreator","email", userDto.getEmail())
        );

        //transforming to user ,so I can store it in the db
        UserMapper.mapToUser(userDto,user);

        //any change the .save() in jpa will knows it and save the new version of user
        userRepository.save(user);


        //if it's returning true it means the update has taken place
        return true;
    }

    /**
     * @param email - Input Email Address
     * @return boolean indicating if the account was deleted or not
     */
    @Override
    public boolean deleteUser(String email) {

        //first I'm going to search for post creator (user)
        User user= userRepository.findUserByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("PostCreator","email", email)
        );

        //then I will search for all his posts then delete them
        Integer numOfPostsDeleted=postsRepository.deletePostsByUser(user);


        //after that I delete the post creator (user)
        if(numOfPostsDeleted >= 0){
            userRepository.deleteById(user.getUserId());
            return  true;
        }else{
            return false;
        }

    }



}
