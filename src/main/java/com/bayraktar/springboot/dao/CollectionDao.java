package com.bayraktar.springboot.dao;

import com.bayraktar.springboot.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDao extends JpaRepository<Collection, Long> {

}
