package com.sh.app.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member") // 생략불가
@Table(name = "tb_member")
@EqualsAndHashCode(of = {"id", "name", "team"})
public class Member {
    @Id
	@Column(length = 20)
	private String id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="team_id") // 대소문자 구분
	private Team team;

	public void setTeam(Team team) {
		this.team = team;
		Optional.ofNullable(team)
				.ifPresent(_team -> {
					if(_team.getMembers() == null)
						_team.setMembers(new ArrayList<>());
					if(!_team.getMembers().contains(this))
						_team.getMembers().add(this);
				});
	}
}
