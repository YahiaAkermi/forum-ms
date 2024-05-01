package com.yahia.forum.service;

import com.yahia.forum.dto.UserDto;

public interface IUserService {

    /**
     *this method will create post creator
     *@param userDto -  UserDto  object
     */

    void createUser(UserDto userDto);


    /**
     * Fetches account details based on a given mobile number.
     *
     * @param email - Input Email Address
     * @return postCreator  Details based on given email
     */
    UserDto fetchUser(String email);


    /**
     * Fetches account details based on a given mobile number.
     *
     * @param userDto - Input post creator
     * @return boolean indicating if updating postCreator details is successfull or not
     */
    boolean updateUser(UserDto userDto);



    /**
     *
     * @param email - Input Email Address
     * @return boolean indicating if the account was deleted or not
     */
    boolean deleteUser(String email);


}
