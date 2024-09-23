package com.enigmacamp.enigmacoop.repository;

import com.enigmacamp.enigmacoop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {
}
