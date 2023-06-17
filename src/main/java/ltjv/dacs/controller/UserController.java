package ltjv.dacs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.entity.User;
import ltjv.dacs.service.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
            }
            return "user/register";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String showAllUsers(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("users", userService.getAllUsers(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", userService.getAllUsers(pageNo, pageSize, sortBy).size() / pageSize);
        return "user/list";
    }

    @GetMapping("/edit/{id}")
    public String editPerfumeForm(@NotNull Model model, @PathVariable String id)
    {
        User editUser = userService.getUserById(id);
        if(editUser != null) {
            model.addAttribute("user", editUser);
            return "user/edit";
        }else{
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/user";
    }
}
