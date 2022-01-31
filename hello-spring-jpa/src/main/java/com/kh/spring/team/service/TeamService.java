package com.kh.spring.team.service;

import java.util.List;

import com.kh.spring.team.domain.Team;

public interface TeamService {

	Team save(Team team);

	List<Team> findAll();

}
