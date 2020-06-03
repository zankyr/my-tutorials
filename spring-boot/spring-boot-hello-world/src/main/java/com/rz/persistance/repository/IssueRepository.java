package com.rz.persistance.repository;

import com.rz.persistance.model.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<IssueReport, Long> {

    @Query(value = "SELECT issue FROM IssueReport issue WHERE issue.markedAsPrivate = false")
    List<IssueReport> findAllButPrivate();

    List<IssueReport> findAllByEmail(String email);
}
