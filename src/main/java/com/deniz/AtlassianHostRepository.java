package com.deniz;

import com.atlassian.connect.spring.AtlassianHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtlassianHostRepository extends JpaRepository<AtlassianHost,String> {
}
