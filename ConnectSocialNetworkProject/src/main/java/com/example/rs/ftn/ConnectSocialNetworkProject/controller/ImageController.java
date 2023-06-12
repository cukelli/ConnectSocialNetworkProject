package com.example.rs.ftn.ConnectSocialNetworkProject.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Image;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ImageRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.ImageService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/image")
public class ImageController {
	
	private final PostService postService;
	private final ImageService imageService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public ImageController(PostService postService,ImageService imageService,UserService
			userService,JwtUtil jwtUtil) {
		this.postService = postService;
		this.imageService = imageService;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@GetMapping("/all")
	@ResponseBody
	public List<Image> getAllImages(Authentication authentication) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		if (!userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin");
		}
		List<Image> images = imageService.findAllUndeletedImages();
		for (Image image: images) {
			System.out.println(image.getId());
		}
		
        return images;
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Image> addImage(Authentication authentication,@RequestBody ImageRequest image) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Image newImage = new Image();
		newImage.setPath(image.getImagePath());

		
		
		return new ResponseEntity<>(imageService.addImage(newImage),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public Message deleteImage(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Image imageForDeletion = imageService.findOne(id);
		
		if (!imageForDeletion.getPostedImageBy().equals(userLogged.getUsername()) ||
				!imageForDeletion.getPostedImageBy().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this post.");
		}
		
		imageForDeletion.setDeleted(true);
		 imageService.updateImage(imageForDeletion);
		return new Message("Image deleted succesfuly.");
		
	}
	
	
	
	
	
	

}
