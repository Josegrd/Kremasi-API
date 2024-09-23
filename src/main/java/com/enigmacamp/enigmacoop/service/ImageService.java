package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile multipartFile);
    Resource getById(String id);

    void deleteById(String id);
}
