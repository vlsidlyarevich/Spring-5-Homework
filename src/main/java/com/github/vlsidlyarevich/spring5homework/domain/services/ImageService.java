package com.github.vlsidlyarevich.spring5homework.domain.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jt on 7/3/17.
 */
public interface ImageService {

    void saveImageFile(String recipeId, MultipartFile file);
}