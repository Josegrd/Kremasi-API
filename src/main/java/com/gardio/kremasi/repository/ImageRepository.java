package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {
}
