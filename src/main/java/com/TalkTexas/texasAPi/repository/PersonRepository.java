package com.TalkTexas.texasAPi.repository;

import com.TalkTexas.texasAPi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Custom repository methods, if needed
}
