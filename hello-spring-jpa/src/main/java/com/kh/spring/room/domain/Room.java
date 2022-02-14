package com.kh.spring.room.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.kh.spring.client.domain.Client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room", uniqueConstraints = {
		@UniqueConstraint(name = "uq_room_client_id", columnNames = {"client_id"})})
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "client")
public class Room implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	public void setClient(Client client) {
		// 이전 client에서 room 제거
		if(this.client != null)
			if(client != null && client.getRoom() != this)
				client.setRoom(null);
		
		this.client = client;
		
		// 양방향 편의
		if(client != null && client.getRoom() != this)
			client.setRoom(this);
	}
	
	@Override
	public String toString() {
		return "Room [id=" + id + ", client=" + (client != null ? client.getId() : null) + "]";
	}
	
	
}
