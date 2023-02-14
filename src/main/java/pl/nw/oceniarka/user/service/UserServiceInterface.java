package pl.nw.oceniarka.user.service;

import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.dto.request.UserRequest;
import pl.nw.oceniarka.user.dto.request.UserRequestDTO;
import pl.nw.oceniarka.user.dto.response.UserResponse;

import java.util.List;

public interface UserServiceInterface {

   UserResponse saveUser(UserRequestDTO userRequest);

   UserResponse findUserById(Long id);

   void deleteUser(Long id);

   UserResponse updateUser(Long id, UserRequest userRequest);

   List<UserResponse> findAllUsers();

    UserResponse getCurrentUser();
}
