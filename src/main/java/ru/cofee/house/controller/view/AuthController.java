package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cofee.house.core.dto.UserDto;
import ru.cofee.house.model.auth.Role;
import ru.cofee.house.model.auth.User;
import ru.cofee.house.service.auth.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }


    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        Optional<User> existingUser = userService.findUserByUsername(userDto.getUsername());

        if (existingUser.isPresent()) {
            result.rejectValue("username", "alreadyRegistered",
                    "Такой пользователь уже зарегистирован");
            result.rejectValue("username", "test",
                    "ttest");

        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUserDto(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/username")
    @ResponseBody
    public ResponseEntity<Map<String, String>> currentUserName(Authentication authentication) {
        if (authentication != null)
            return ResponseEntity.ok(Map.of("username", authentication.getName()));
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(value = "/iamisadmin")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> iAmIsAdmin(Authentication authentication) {
        if (authentication != null)
            return ResponseEntity.ok(Map.of("isAdmin",
                    authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> {
                                String authority = grantedAuthority.getAuthority();
                                return authority
                                        .equals(Role.ADMIN.getAuthority());
                            })
            ));
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}