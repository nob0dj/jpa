package com.sh.entity.association._01.one2one._01.non.identifying.user.card.repository;

import com.sh.entity.association._01.one2one._01.non.identifying.user.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
