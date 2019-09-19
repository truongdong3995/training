package com.training.api.repositorys;

import com.training.api.entitys.TblPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<TblPost, Integer>{

}
