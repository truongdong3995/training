package com.training.api.repository;

import com.training.api.entity.TblPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<TblPost, Integer>{

}
