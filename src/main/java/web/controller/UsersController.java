package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {
    public UsersController() {
    }
    @Autowired
    private UserService userService;


//    public UsersController(UserService userService) {
//        this.userService = userService;
//    }


    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String listUsers(Model model) {
        List<User> userList=userService.listUsers();
        //model.addAttribute("user", new User());
        model.addAttribute("listUsers", userList);

        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        if (user.getId() == 0) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);

        return "redirect:/users";
    }

    @RequestMapping("/{id}/update/")
    public String updateUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("listUsers", userService.listUsers());

        return "users/update";
    }

    @RequestMapping("/userData/{id}")
    public String userData(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));

        return "userData";
    }
}
