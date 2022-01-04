package net.javaguides.springboot.controller;

import java.net.http.HttpClient.Redirect;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.RedirectView;

import net.javaguides.springboot.model.Application;
import net.javaguides.springboot.repository.ApplicationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	// display list of Applications
	
	@RequestMapping(value="/")
	 public ModelAndView screen1(Model model) 
	 {		
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("screen1.html");
	        
	        
	        return modelAndView;
	    }  
	
	@GetMapping("/checkUser") 
	public String checkUser(@RequestParam(name="identity", required = false) String identity,
			@RequestParam(name="sifre", required = false) String sifre,
			Model model) 
	{
		
		String sql = "SELECT `sifre` FROM `user`.`user` WHERE `identity`='" + identity + "';";
		
		String sifre1 = (String) jdbcTemplate.queryForObject(sql, String.class);
		
		System.out.println(sifre1);
		System.out.println(sifre);
		System.out.println(sifre.equals(sifre1));
		
		if(sifre.equals(sifre1))
		{
			return "screen3";
		}
		else 
		{
			return "screen2";
		}
	}
	
	@GetMapping("/count") 
	public String count(@RequestParam(name="ulke", required = false) String ulke,
			Model model) 
	{
		
		int size = applicationRepository.findBy(ulke);
	    System.out.println(size);
	    model.addAttribute("ulke", size);
    
	    return "showCases";
	}
    
	@GetMapping("/saveUser") 
	public String saveUser(@RequestParam(name="name", required = false) String name,
			@RequestParam(name="surname", required = false) String surname,
			@RequestParam(name="identity", required = false) String identity,
			@RequestParam(name="tel", required = false) String tel,
			@RequestParam(name="sifre", required = false) String sifre,
			Model model) 
	{
		String perm = "0";
		String sql = "INSERT INTO `user`.`user` (`name`, `surname`,`identity`,`tel`,`sifre`,`perm`) VALUES ( ?, ?, ?,  ?, ?, ?)";
		
		jdbcTemplate.update(sql,name, surname,identity,tel,sifre,perm);
		
		return "screen2";
	}
	
	@GetMapping(value = "/saveOffer/{id}/{offer}") 
	public String saveUser(@PathVariable String id,
			@PathVariable String offer,
			Model model) 
	{
		
		//UPDATE `user`.`application` SET `offer` = '12' WHERE (`id` = '5');
		/*System.out.println(offer);
		String sql = "UPDATE `user`.`application` SET '"+ offer +"' = '12' WHERE (`id` = '"+id+"');";
	
		jdbcTemplate.update(sql);*/
		Application app = new Application();
		
		return "screen4";
	}
	
	

			
	@GetMapping("/saveApplication") 
	public String saveApplication(@RequestParam(name="name", required = false) String name,
			@RequestParam(name="surname", required = false) String surname,
			@RequestParam(name="place", required = false) String place,
			@RequestParam(name="start", required = false) String start,
			@RequestParam(name="end", required = false) String end,
			@RequestParam(name="identity", required = false) String identity,
			@RequestParam(name="tel", required = false) String tel,
			@RequestParam(name="mail", required = false) String mail,
			Model model) 
	{
		
		String id = "1";
		try 
		{
			
			String sql1 = "SELECT `iduser` FROM `user`.`user` WHERE `identity`='" + identity + "';";
			
			
			id = (String) jdbcTemplate.queryForObject(
			            sql1, String.class);
		}
		catch (Exception e) 
		{
			return "error";
		}
		
		try 
		{
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
			java.util.Date format1 = format.parse(start);
			java.util.Date format2 = format.parse(end);
			
			java.sql.Date start1 = new java.sql.Date(format1.getTime());
			java.sql.Date end1 = new java.sql.Date(format2.getTime()); 
			
			String sql = "INSERT INTO `user`.`application` (`kullaniciid`, `name`, `surname`, `place`,`start`, `end`,`identity`,`tel`,`mail`) VALUES ( ?,?, ?, ?,  ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql, id,name, surname, place,start1, end1,identity,tel,mail);
		} 
		catch (Exception e) 
		{
			return "error2";
		}
		
		
		return "screen4";
	}
	
	@GetMapping(value = "/showApp/{id}")
	public String showApp(Model model,@PathVariable String id) 
	{
	        
	        List<Application> listApplications = applicationRepository.findAll();
	        int idd =Integer.parseInt(id);  
	        System.out.println(idd);
	        model.addAttribute("id", idd);
	        model.addAttribute("applications", listApplications);
	        return "showApp";
	}
	
	@GetMapping("/screen3")
	public String screen3() 
	{
		
		return "screen3";
	}

	
	@GetMapping("/showCases")
	public String showCases() 
	{
		
		return "showCases";
	}

	
	@GetMapping("/screen4")
	public String screen4(Model model) {
		
		List<Application> listApplications = applicationRepository.findAll();
		
		System.out.println(listApplications);
		
		model.addAttribute("applications",listApplications);
		
		return "screen4";		
	}
	
	@GetMapping("/screen2")
	public String screen2(Model model) 
	{
		return "screen2";		
	}
	
	@GetMapping("/screen5")
	public String screen5(Model model) {
		return "screen5";		
	}
	
	@GetMapping("/screen6")
	public String screen6(Model model) {
		return "screen6";		
	}
		
	
	
	
	
	

}
