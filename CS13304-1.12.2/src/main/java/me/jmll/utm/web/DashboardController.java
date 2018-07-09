package me.jmll.utm.web;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Maneja requests
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {
	private static final Logger log = LogManager.getLogger(DashboardController.class);
	
	@RequestMapping(value = {"", "home"}, 
					method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
		log.info("Dashboard call");
        return "dashboard/home";
    }
}
