package com.kh.spring.team.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.team.domain.Team;
import com.kh.spring.team.repository.TeamRepository;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team save(Team team) {
		return teamRepository.save(team);
	}

	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}
	
	
	
	
}
