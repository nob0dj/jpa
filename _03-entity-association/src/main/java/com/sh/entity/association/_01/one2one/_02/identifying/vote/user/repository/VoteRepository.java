package com.sh.entity.association._01.one2one._02.identifying.vote.user.repository;

import com.sh.entity.association._01.one2one._02.identifying.vote.user.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, String> {
}
