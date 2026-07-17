package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import library_management.dto.UserDto;
import library_management.entity.User;
import library_management.entity.UserRole;
import library_management.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {

        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", UserRole.values());

        return "user-form";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("userDto") UserDto dto,
            BindingResult result,
            Model model) {

        if (dto.getPassword() == null
                || dto.getPassword().isBlank()) {

            result.rejectValue(
                    "password",
                    "error.password",
                    "Password is required");

        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "roles",
                    UserRole.values());

            return "user-form";
        }

        User user = new User();

        user.setFullname(dto.getFullname());

        user.setEmail(dto.getEmail());

        user.setPassword(dto.getPassword());

        user.setRole(dto.getRole());

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        User user = userService.getUserById(id);

        UserDto dto = new UserDto();

        dto.setId(user.getId());

        dto.setFullname(user.getFullname());

        dto.setEmail(user.getEmail());

        dto.setRole(user.getRole());

        model.addAttribute(
                "userDto",
                dto);

        model.addAttribute(
                "roles",
                UserRole.values());

        return "user-form";
    }

    @PostMapping("/update")
    public String updateUser(
            @Valid @ModelAttribute("userDto") UserDto dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "roles",
                    UserRole.values());

            return "user-form";
        }

        User user = userService.getUserById(dto.getId());

        user.setFullname(dto.getFullname());

        user.setEmail(dto.getEmail());

        user.setRole(dto.getRole());

        if (dto.getPassword() != null
                && !dto.getPassword().isBlank()) {

            user.setPassword(
                    dto.getPassword());

        }

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
public String deleteUser(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes) {


    try {

        userService.delete(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "User deleted successfully"
        );


    } catch (Exception e) {

        redirectAttributes.addFlashAttribute(
                "error",
                "Cannot delete user because borrow records exist"
        );
    }


    return "redirect:/users";
}

}
