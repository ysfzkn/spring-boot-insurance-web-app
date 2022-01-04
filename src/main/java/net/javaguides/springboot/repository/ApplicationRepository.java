package net.javaguides.springboot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.Application;
import net.javaguides.springboot.model.ApplicationRowMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Repository
@Transactional
public class ApplicationRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    public List<Application> findAll() {

        String sql = "SELECT * FROM `user`.`application`";
        
        List<Application> list = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Application.class));
        
        	return list;
    }
    
    public int findBy(String keyword) {

        String sql = "SELECT * FROM `user`.`application` WHERE `application`.`place` LIKE '" + keyword + "';";
        
        List<Application> list = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Application.class));
        
        System.out.println(list);
        System.out.println(list.size());
        
        int size = list.size();
        
        return size;	
    }
    
    /*@Autowired
    private Application findById(String id) {

        String sql = "SELECT * FROM `user`.`application` WHERE `id`= '" + id + "';";

        return (Application) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Application.class));

    } */

    

    
}
