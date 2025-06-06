package swe4.saju_taro.service;

import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserRequestDTO;
import swe4.saju_taro.dto.UserResponseDTO;

public interface UserService {
    public UserResponseDTO.UserDTO createUser (UserRequestDTO.UserDTO userDTO);
    public User getUser(Integer id);
    public void deleteUser(Integer userId);
    public void updateUser(Integer userId, UserRequestDTO.UserDTO userDTO);
}
