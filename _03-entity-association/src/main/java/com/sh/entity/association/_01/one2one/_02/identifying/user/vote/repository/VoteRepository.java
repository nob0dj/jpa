package com.sh.entity.association._01.one2one._02.identifying.user.vote.repository;

import com.sh.entity.association._01.one2one._02.identifying.user.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, String> {
}
