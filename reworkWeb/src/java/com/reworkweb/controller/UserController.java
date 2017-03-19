
package com.reworkweb.controller;


import com.reworkweb.model.UserReworkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mchamparini
 */
@Controller
public class UserController{
    
    @Autowired
    private UserReworkDao user_reworkdao;
 
    @RequestMapping(value="/index.htm",method = RequestMethod.POST)
    public String loginPOST( @RequestParam("txtName") String txtName
                            ,@RequestParam("txtPassword") String txtPassword
                            ,Model model
                            ){      
             
            if(user_reworkdao.getUserRework(txtName.trim(),txtPassword.trim())!=null){
                return "home_cp";
            }
            else{
               return "index";
           }
    }        
    @RequestMapping(value="/index.htm",method = RequestMethod.GET)
    public String userGET(Model model){
        return "index";
    }        
    
}
