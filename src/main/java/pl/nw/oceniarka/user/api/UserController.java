package pl.nw.oceniarka.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.nw.oceniarka.user.dto.request.NewPasswordRequestDTO;
import pl.nw.oceniarka.user.dto.request.UserRequest;
import pl.nw.oceniarka.user.dto.request.UserRequestDTO;
import pl.nw.oceniarka.user.dto.response.UserResponse;
import pl.nw.oceniarka.user.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(tags = "User")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ApiOperation("Show all users")
    public ResponseEntity<List<UserResponse>> showAllUser(){
        List<UserResponse> userResponseList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @PostMapping
    @ApiOperation("Create user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequestDTO userRequest){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.created(uriComponentsBuilder.path("/api/users/{id}").build(userResponse.getId())).body(userResponse);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> sendEmailWithToken(@RequestBody String emailRequest){
        userService.saveTokenAndSendEmail(emailRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path ="confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok().body(userService.confirmToken(token));
    }

    @GetMapping("/{id}")
    @ApiOperation("Find user")
    public ResponseEntity<UserResponse> findUser(@PathVariable Long id){
        UserResponse userResponse = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update user")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponseDTO = userService.getCurrentUser();
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/password/reset")
    public void resetPassword(String token, HttpServletResponse response) {
        try {
            userService.validateToken(token);
            response.setHeader("Location", "http://localhost:4200/#/new-password/" + token);
            response.setStatus(HttpStatus.FOUND.value());
        } catch (Exception tokenExpiredException) {
            response.setHeader("Location", "http://localhost:4200/#/tokenExpired");
            response.setStatus(HttpStatus.FOUND.value());
            // Found means that we expect such exception, and we have solution for that.
            // We invoke tokenExpired address in case of such situation.
        }
    }

    @PostMapping("/password/new")
    public ResponseEntity<Void> saveNewPassword(@RequestBody NewPasswordRequestDTO newPasswordRequestDTO) {
        try {
            userService.saveNewPassword(newPasswordRequestDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception tokenExpiredException) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}