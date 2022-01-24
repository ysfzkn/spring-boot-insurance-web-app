package net.javaguides.springboot.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.javaguides.springboot.model.Application;
import net.javaguides.springboot.repository.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;



@Controller
public class ApplicationController {
	
	private static final String APP = "applications";
	private static final String S2 = "screen2";
	private static final String S4 = "redirect:/screen4";
	private static final String S5 = "redirect:screen5/";
	
	
    @Autowired
    ApplicationRepository applicationRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	int id;
	
	// display list of Applications
	
	@RequestMapping(value="/" ,method = RequestMethod.GET)
	 public ModelAndView screen1(Model model) 
	 {		
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("screen1.html");
	        
	        
	        return modelAndView;
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
	
	@GetMapping(value ="/screen4/{id}")
	public String saveUser(@PathVariable String id,
			@RequestParam(name="offer", required = true) String offer,
			Model model) 
	{
		try
		{
			
			System.out.println(offer);
			String sql = "UPDATE `user`.`application` SET `offer` = '"+ offer +"' WHERE (`id` = '"+id+"');";
		
		
			jdbcTemplate.update(sql);
			System.out.println(id);
			
			return S4;
		}
		catch(Exception e)
		{
			return "error";
		}
	
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
		
		return S5+identity+"";
	}
	
	@GetMapping("/checkUser") 
	public String checkUser(@RequestParam(name="identity", required = false) String identity,
			@RequestParam(name="sifre", required = false) String sifre,
			Model model) 
	{
	
		
		try 
		{
			String sql = "SELECT `sifre` FROM `user`.`user` WHERE `identity`='" + identity + "';";
			
			String sql1 = "SELECT `perm` FROM `user`.`user` WHERE `identity`='" + identity + "';";
			
			String perm = jdbcTemplate.queryForObject(sql1, String.class);
			
			String sifre1 = jdbcTemplate.queryForObject(sql, String.class);
			
			System.out.println(sifre1);
			System.out.println(sifre);
			System.out.println(sifre.equals(sifre1));
			
			if(sifre.equals(sifre1))
			{
				if(perm == null)
				{
					return "redirect:/error2";
				}
				else if(perm.equals("1"))
				{
					return S4;
				}
				else
				{
					return S5+identity+"";
				}
				
			}
			else 
			{
				return S2;
			}
			
		} 
		catch (Exception e) 
		{
			return S2;
		}
		
	}
	
	@GetMapping("/screen5/{id}")
	public String screen5(@PathVariable String id, Model model) 
	{
		try 
		{
			List<Application> listApplications = applicationRepository.findAll();
			
			
			List<Application> newlist = findByIdentity(listApplications, id);
			Application user = newlist.get(0);
			
			String username = user.getName();
			String surname = user.getSurname();
			
			String fullname = username + " " + surname ;
			
			System.out.println(fullname);
			System.out.println(newlist);
			
			model.addAttribute("username", fullname);
			model.addAttribute(APP,newlist);
		} 
		catch (Exception e) 
		{
			String fullname = "Lütfen Önce Teklif Alınız ->";
			List<Application> newlist = new ArrayList<>();
			model.addAttribute("username", fullname);
			model.addAttribute(APP,newlist);
		}
		
			
		return "screen5";		
	}
	

	public List<Application> findByIdentity(List<Application> applications, String identity)
	{
		 Iterator<Application> iterator = applications.iterator();
		 List<Application> list = new ArrayList<>();
		 
		 
		    while (iterator.hasNext()) 
		    {
		        Application application = iterator.next();
		        
		        String id =application.getIdentity();
		       
		        
		        if (id.equals(identity)) 
		        {		        	
		            list.add(application);
		        }
		    }
		    return list;
		
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
			
			
			id =  jdbcTemplate.queryForObject(
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
		
		
		return S5+identity+"";
	}
	
	@GetMapping(value = "/showApp/{id}")
	public String showApp(Model model,@PathVariable String id) 
	{
	        
	        List<Application> listApplications = applicationRepository.findAll();
	        int idd =Integer.parseInt(id);  
	        System.out.println(idd);
	        
	        Application app = findUsingIterator(id, listApplications);
	        
	        model.addAttribute("id", idd);
	        
	        model.addAttribute(APP, app);
	        return "showApp";
	}
	
	
	public Application findUsingIterator(String id, List<Application> applications) 
	{
	    Iterator<Application> iterator = applications.iterator();
	    while (iterator.hasNext()) {
	        Application application = iterator.next();
	        
	        String idd =Long.toString( application.getId());
	        
	        if (idd.equals(id)) {
	        	System.out.println(application);
	            return application;
	        }
	    }
	    
	    return null;
	}
	
	public List<Application> findHaventOffer(List<Application> applications)
	{
		 Iterator<Application> iterator = applications.iterator();
		 List<Application> listoff = new ArrayList<>();
		 
		 
		    while (iterator.hasNext()) 
		    {
		        Application application = iterator.next();
		        
		        String off =application.getOffer();
		        System.out.println(off);
		        if (off == null) 
		        {
		        	System.out.println(application);
		        	
		            listoff.add(application);
		        }
		    }
		    return listoff;
		
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
		
		System.out.println("liste");
		System.out.println(listApplications);
		
		List<Application> newlist = findHaventOffer(listApplications);
		
		System.out.println(newlist);
		
		model.addAttribute(APP,newlist);
		
		return "screen4";		
	}
	
	@GetMapping("/screen2")
	public String screen2(Model model) 
	{
		return S2;		
	}
	

	
	@GetMapping("/screen6")
	public String screen6(Model model) {
		return "screen6";		
	}
		
	
	
	
	
	

}
