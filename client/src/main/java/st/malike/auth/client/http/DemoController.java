/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.client.http;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author malike_st
 */
@Controller
@RequestMapping(value = "/Demo")
public class DemoController {

    @RequestMapping(value="/welcome")
    @ResponseBody
    public String Welcome() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> rst = auth.getAuthorities();
        List<String> right = rst.stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        String rights = String.join(",", right);
        return String.format("Hello Welcome %s. User Right: %s .", auth.getName(), rights);
    }

}
