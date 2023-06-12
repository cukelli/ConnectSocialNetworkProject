package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.PostNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Image;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.ImageRepo;

@Service
public class ImageService {
	
private final ImageRepo imageRepo;

@Autowired
public ImageService(ImageRepo imageRepo) {
	this.imageRepo = imageRepo;
}

   public Image findOne(Long id) {
		return imageRepo.findById(id).orElseThrow(() -> 
		new PostNotFoundException("Image by id " + id + "was not found"));
	}

	public List<Image> findAll() {
		return imageRepo.findAll();
	}
	
	public Page<Image> findAll(Pageable page) {
		return imageRepo.findAll(page);
	}
	
	public List<Image> findAllUndeletedImages() {
		return imageRepo.findAllByIsDeletedFalse();
		
	}
	public void remove(Long id) {
		imageRepo.deleteById(id);
	}
	
	public Image updateImage(Image image) {
		return imageRepo.save(image);
	}
	
	 public Image addImage(Image image) {   
	       return imageRepo.save(image);
  }
	 public List<Image> findAllByBelongsTo(Post post) {
			return imageRepo.findAllByBelongsTo(post);
			
		}
	 
	 
	

}
