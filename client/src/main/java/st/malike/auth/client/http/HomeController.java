package st.malike.auth.client.http;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;

/**
 * Created by fengdaqing on 2018/5/2.
 */
@Controller
@RequestMapping(value = "/Home")
public class HomeController {

    @RequestMapping(value = {"/", "/Index"}, method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ModelAndView Index(@RequestParam(value = "Name", required = false) String Name,
                              Principal user) {
        Collection<? extends GrantedAuthority> rst = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ModelAndView model = new ModelAndView("Home/Index");
        if (user != null && !StringUtils.isEmpty(user.getName())) {
            Name = user.getName();
        }
        model.addObject("Name", Name);
        return model;
    }
}
