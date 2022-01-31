package com.kh.spring.team.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.team.domain.Team;
import com.kh.spring.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService teamService;

	@PostMapping("/{name}")
	public ResponseEntity<?> team(@PathVariable String name){
		Team team = new Team(name);
		teamService.save(team);
		return ResponseEntity.ok(team);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> all(){
		List<Team> list = teamService.findAll();
		return ResponseEntity.ok(list);
	}
}
