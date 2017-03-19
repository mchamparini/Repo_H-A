
package com.reworkweb.controller;

import com.reworkweb.model.ProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mchamparini
 */
@Controller
public class ProcessController {

    @Autowired
    private ProcessDao process_dao;
    
    @RequestMapping(value = "/show_process.htm", method = RequestMethod.GET)
    public String shocdwprocessGET(Model model) {
        model.addAttribute("listProcessToRun", process_dao.getAllProcessToRun());
        return "home_cp";
    }
    @RequestMapping(value="/showCP.htm",method=RequestMethod.GET)
    public String showCP(Model model){
        return "home_cp";
    }       
    @RequestMapping(value = "/{processName}.htm",method = RequestMethod.GET)
    public String execProcessGET(@PathVariable("processName") String processName
                                ,@RequestParam("txtParam") String param
                                ,Model model){
        String param1=null;        
        String error=null;
        try {
              param1 = process_dao.findProcess(processName).getProcessPath();            
            if(param.contains(";")){
                param = param.replaceAll(";"," ");
            }
            model.addAttribute("txtResultCommand",process_dao.executeRemoteLS(param1+" "+param));
            //process_dao.executeRemoteLS(param1);
        }catch (Exception e) {
              error = e.getMessage();
              model.addAttribute("error_jpql",error);
        }              
       // model.addAttribute("txtParam",param1+" "+param);       
        return "home_cp";
    }
    
    @RequestMapping(value="/shell.htm",method=RequestMethod.GET)
    public String execShellTest(@RequestParam("txtShell") String txtShell,Model model){
        model.addAttribute("txtResultCommand",process_dao.executeRemoteLS(txtShell));
        return "home_cp";
    }
    
    @RequestMapping(value="/label.htm",method=RequestMethod.GET)
    public String execLabel(@RequestParam() String txtLabel,Model model){
        
        return "show_process";
    }
}