package archapp.web;

import archapp.model.User;
import archapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/persons")
    public List<User> persons(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value="name", required = false) String name,@RequestParam(value="activity", required = false) String activity) {
        if (page < 1) page = 1;
        if (name != null && !name.isEmpty())
            return userRepository.searchByName(name, PageRequest.of(page - 1, 10)).toList();
        if(activity!=null && !activity.isEmpty())
            return userRepository.searchByActivityTitle(activity,PageRequest.of(page-1,10)).toList();

        return userRepository.findAll(PageRequest.of(page - 1, 10)).toList();
    }
}
