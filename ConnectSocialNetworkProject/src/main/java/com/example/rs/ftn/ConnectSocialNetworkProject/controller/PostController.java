package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.FileService;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl.IndexingServiceImpl;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.LoadingException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.StorageException;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Image;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostUpdate;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ReactionCount;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	private final FriendRequestService friendRequestService;
	private final FileService fileService;
	private final UserService userService;
	private final ImageService imageService;
	private final SearchService searchService;
	private final ReactionService reactionService;
	private final CommentService commentService;
	private final JwtUtil jwtUtil;
	private final IndexingServiceImpl indexingServiceImpl;



	public PostController(PostService postService, FileService fileService, UserService
			userService, ImageService imageService, ReactionService reactionService,
                          CommentService commentService, JwtUtil jwtUtil, SearchService searchService,
                          IndexingServiceImpl indexingServiceImpl, FriendRequestService friendRequestService) {
		this.postService = postService;
        this.fileService = fileService;
        this.userService = userService;
		this.imageService = imageService;
		this.reactionService = reactionService;
		this.commentService = commentService;
		this.searchService = searchService;
		this.jwtUtil = jwtUtil;
		this.indexingServiceImpl = indexingServiceImpl;
		this.friendRequestService = friendRequestService;
	}
	
	 @GetMapping("/all")
	    @ResponseBody
	    public List<Post> getAllPosts(Authentication authentication,
	                                  @RequestParam(value = "sort", defaultValue = "asc") String sort) {
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

	        List<Post> posts = postService.findAllUndeletedPosts();

	        if (sort.equalsIgnoreCase("asc")) {
	            posts.sort(Comparator.comparing(Post::getCreationDate));
	        } else if (sort.equalsIgnoreCase("desc")) {
	            posts.sort(Comparator.comparing(Post::getCreationDate).reversed());
	        }

	        return posts;
	    }
	
	@PostMapping(value ="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Post> addPost(Authentication authentication,
										@RequestParam(value = "postContentPdf", required = false)
										MultipartFile postContentPdf,
										@RequestPart("postRequest") PostRequest post) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		Post newPost = new Post();
		newPost.setContent(post.getContent());
		newPost.setTitle(post.getTitle());
		newPost.setCreationDate(LocalDateTime.now());
		newPost.setUser(userLogged);
		
		Post dbPost = postService.addPost(newPost);
		
		  if (post.getImage() != null && !post.getImage().isEmpty()) {
		        Image image = new Image();
		        image.setPath(post.getImage());
		        image.setPostedImageBy(userLogged);
		        image.setBelongsTo(dbPost);
		        imageService.addImage(image);
		    }

		  PostIndex postIndex = new PostIndex();
		  postIndex.setTitle(newPost.getTitle());
		  postIndex.setContent(newPost.getContent());
		  postIndex.setCreationDate(LocalDate.now());
		postIndex.setDatabaseId(newPost.getId());
		postIndex.setDeleted(newPost.isDeleted());
		postIndex.setPostLikes(0L);
		  postIndex.setUser(newPost.getUser());

		if (postContentPdf != null && !postContentPdf.isEmpty()) {
			try {
				String mimeType = indexingServiceImpl.detectMimeType(postContentPdf);
				String extractedDocumentIndex = indexingServiceImpl.extractDocumentContent(postContentPdf);
				System.out.println(extractedDocumentIndex + " extracted text from pdf group");
				postIndex.setPdfContent(extractedDocumentIndex);
				fileService.store(postContentPdf, postIndex.getTitle() +
						" post description pdf" + UUID.randomUUID().toString());
			} catch (StorageException | LoadingException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
		searchService.save(postIndex);

		return new ResponseEntity<>(dbPost, HttpStatus.OK);
	}
	
//	@PostMapping("/images/{postId}")
//	public Message createImage(Authentication authentication,@RequestBody PostRequest post) {
//		String username = authentication.getName();
//		User userLogged = null;
//		try {
//			userLogged = userService.findOne(username);
//		} catch (UserNotFoundException e) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
//		}
//		
//
//		
//		
//		return new ResponseEntity<>(postService.addPost(newPost),HttpStatus.OK);
//	}
//	
	
	
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<Post> updatePostApi(Authentication authentication,@RequestBody PostUpdate post,
			@PathVariable("postId") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Post oldPost = postService.findOne(id);
		oldPost.setContent(post.getContent());
		
		if (oldPost.getUser().toString() != userLogged.getUsername().toString()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this post.");
		}
		//oldPost.setUser(userLogged);
		
		
		Post updatedPost = postService.updatePost(oldPost);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public Message deletePost(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Post postForDeletion = postService.findOne(id);
		
		if (!postForDeletion.getUser().equals(userLogged.getUsername()) ||
				!postForDeletion.getUser().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this post.");
		}
		
		 postForDeletion.setDeleted(true);
		 postService.updatePost(postForDeletion);
		return new Message("Post deleted succesfuly.");
		
	}
	
	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity<List<Post>> getUserPosts(Authentication authentication,
	        @RequestParam(value = "sort", defaultValue = "asc") String sort) {
	    String userUsername = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(userUsername);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    if (!userLogged.getUsername().equals(userUsername) && !userLogged.getRole().toString().equals("ADMIN")) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin/you are not the creator of those posts.");
	    }

	    List<Post> posts;
	    if (userLogged.getRole().toString().equals("ADMIN")) {
	        posts = postService.findAllUndeletedPosts();
	    } else {
	        posts = postService.getUserPosts(userLogged.getUsername());
	    }

	    if (sort.equalsIgnoreCase("asc")) {
	        posts.sort(Comparator.comparing(Post::getCreationDate));
	    }
	    else if (sort.equalsIgnoreCase("desc")) {
	        posts.sort(Comparator.comparing(Post::getCreationDate).reversed());
	    }

	    for (Post p : posts) {
	        List<Image> postImages = imageService.findAllByBelongsTo(p);
	        p.setImagePaths(postImages);
	    }

	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/search/byLikeRange")
	public List<PostIndex> searchPostsByLikeRange(Authentication authentication,@RequestParam(required = false) Long minLikes,
													@RequestParam(required = false) Long maxLikes) {
		User userLogged = null;
		String username = authentication.getName();

		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		List<FriendRequest> friendRequestsSentBy;
		friendRequestsSentBy = friendRequestService.findAllByApprovedTrueAndSentBy(userLogged);
		List<String> usernames = friendRequestsSentBy.stream().map(FriendRequest::getSentFor).collect(Collectors.toList());
		List<FriendRequest> friendRequestsSentFor;
		friendRequestsSentFor = friendRequestService.findAllByApprovedTrueAndSentFor(userLogged);
		usernames.addAll(friendRequestsSentFor.stream().map(FriendRequest::getSentBy).collect(Collectors.toList()));
		usernames.add(username);

		List<PostIndex> postIndexes;

		return searchService.findByPostLikesBetween((ArrayList<String>) usernames, minLikes, maxLikes);
	}



	@GetMapping("/search/byLikeRange/{groupId}")
	public List<PostIndex> searchPostsByLikeRangeAndGroup(Authentication authentication,@RequestParam(required = false) Long minLikes,
												  @RequestParam(required = false) Long maxLikes,																		@PathVariable("groupId") Long groupId) {

		User userLogged = null;
		String username = authentication.getName();

		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		return searchService.findByGroupPostedAndPostLikesBetween(groupId, minLikes, maxLikes);
	}

	@GetMapping("/{postId}")
	public Object getPostDetails(Authentication authentication, @PathVariable("postId") Long postId) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }
	    
	    Post post = postService.findOne(postId);
//	    List<Comment> undeletedComments = commentService.findAllByCommentedPostAndIsDeletedFalse(post); // Fetch undeleted comments for the post
	    post.setComments(null); // Set the undeleted comments in the post
//	     for (Comment comment: undeletedComments) {
//			System.out.println(comment.getId());
//		}
	    return post;
	   
	}
	
	@GetMapping("/reactions/{postId}")
	@ResponseBody
	public ReactionCount countReactions(Authentication authentication, @PathVariable("postId") Long postId) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    Post post = postService.findOne(postId);
	    if (post == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found.");
	    }
	    
	    long hearts = reactionService.countByPostReactedAndType(post, ReactionType.HEART);
	    long likes = reactionService.countByPostReactedAndType(post, ReactionType.LIKE);
	    long dislikes = reactionService.countByPostReactedAndType(post, ReactionType.DISLIKE);
	    
	    ReactionCount reactionCount = new ReactionCount(hearts,dislikes,likes);
	    
	    return reactionCount;
	}

	@GetMapping("/searchByTitle")
	public ResponseEntity<List<PostIndex>> searchPostsByTitle(Authentication authentication,
																	 @RequestParam(required = true) String title) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		List<FriendRequest> friendRequestsSentBy;
		friendRequestsSentBy = friendRequestService.findAllByApprovedTrueAndSentBy(userLogged);
		List<String> usernames = friendRequestsSentBy.stream().map(FriendRequest::getSentFor).collect(Collectors.toList());
		List<FriendRequest> friendRequestsSentFor;
		friendRequestsSentFor = friendRequestService.findAllByApprovedTrueAndSentFor(userLogged);
		usernames.addAll(friendRequestsSentFor.stream().map(FriendRequest::getSentBy).collect(Collectors.toList()));
		usernames.add(username);

		List<PostIndex> searchResults = searchService.findAllByUserInAndTitle((ArrayList<String>) usernames, title);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/searchByTitle/{groupId}")
	public ResponseEntity<List<PostIndex>> searchPostsByTitleAndGroupId(Authentication authentication,
															  @RequestParam(required = true) String title,
																		@PathVariable("groupId") Long groupId) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		List<PostIndex> searchResults = searchService.findByTitleAndGroupPosted(title, groupId);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/searchByContentOrPdfContent")
	public ResponseEntity<List<PostIndex>> searchPostsByContent(Authentication authentication,
																	 @RequestParam(required = false) String content,
																	 @RequestParam(required = false) String pdfContent
																    ) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		List<FriendRequest> friendRequestsSentBy;
		friendRequestsSentBy = friendRequestService.findAllByApprovedTrueAndSentBy(userLogged);
		List<String> usernames = friendRequestsSentBy.stream().map(FriendRequest::getSentFor).collect(Collectors.toList());
		List<FriendRequest> friendRequestsSentFor;
		friendRequestsSentFor = friendRequestService.findAllByApprovedTrueAndSentFor(userLogged);
		usernames.addAll(friendRequestsSentFor.stream().map(FriendRequest::getSentBy).collect(Collectors.toList()));
		usernames.add(username);

		Set<PostIndex> searchResults = new HashSet<>();
		List<PostIndex> tmp1 = searchService.findAllByUserInAndContent((ArrayList<String>) usernames, content);
		tmp1.addAll(searchService.findAllByUserInAndPdfContent((ArrayList<String>) usernames, pdfContent));
		searchResults.addAll(tmp1);
		return new ResponseEntity<>(new ArrayList<>(searchResults), HttpStatus.OK);
	}



	@GetMapping("/searchByContentOrPdfContent/{groupId}")
	public ResponseEntity<List<PostIndex>> searchPostsByContentAndGroupId(Authentication authentication,
																@RequestParam(required = false) String content,
																@RequestParam(required = false) String pdfContent,
																					  @PathVariable("groupId") Long groupId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Set<PostIndex> searchResults = new HashSet<>();
		List<PostIndex> tmp1 = searchService.findByContentAndGroupPosted(content, groupId);
		tmp1.addAll(searchService.findByPdfContentAndGroupPosted(pdfContent, groupId));
		searchResults.addAll(tmp1);

		return new ResponseEntity<>(new ArrayList<>(searchResults), HttpStatus.OK);
	}




	@GetMapping("/all/elastic")
	public ResponseEntity<List<PostIndex>> getAllPostIndexes(
			Authentication authentication) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		List<PostIndex> searchResults = searchService.getAllPostIndexes();
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}


	@GetMapping("/user/elastic")
	@ResponseBody
	public ResponseEntity<List<PostIndex>> getUserPostIndexes(
			Authentication authentication,
			@RequestParam(value = "sort", defaultValue = "asc") String sort) {
		String userUsername = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(userUsername);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		if (!userLogged.getUsername().equals(userUsername) && !userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin/you are not the creator of those posts.");
		}

		List<FriendRequest> friendRequestsSentBy;
		friendRequestsSentBy = friendRequestService.findAllByApprovedTrueAndSentBy(userLogged);
		List<String> usernames = friendRequestsSentBy.stream().map(FriendRequest::getSentFor).collect(Collectors.toList());
		List<FriendRequest> friendRequestsSentFor;
		friendRequestsSentFor = friendRequestService.findAllByApprovedTrueAndSentFor(userLogged);
		usernames.addAll(friendRequestsSentFor.stream().map(FriendRequest::getSentBy).collect(Collectors.toList()));
		usernames.add(userUsername);
		List<PostIndex> postIndexes;

		if (userLogged.getRole().toString().equals("ADMIN")) {
			postIndexes = searchService.getAllPostIndexes();
		} else {
			postIndexes = searchService.findAllByUserIn((ArrayList<String>) usernames);
		}
		if (sort.equalsIgnoreCase("asc")) {
			postIndexes.sort(Comparator.comparing(PostIndex::getCreationDate));
		} else if (sort.equalsIgnoreCase("desc")) {
			postIndexes.sort(Comparator.comparing(PostIndex::getCreationDate).reversed());
		}

		return new ResponseEntity<>(postIndexes, HttpStatus.OK);
	}

	
}
