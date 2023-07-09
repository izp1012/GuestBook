package com.study.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}