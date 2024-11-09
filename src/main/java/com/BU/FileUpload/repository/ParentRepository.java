package com.BU.FileUpload.repository;

import com.BU.FileUpload.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Integer> {
}
