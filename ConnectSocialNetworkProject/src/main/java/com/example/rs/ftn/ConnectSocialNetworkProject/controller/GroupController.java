package com.example.rs.ftn.ConnectSocialNetworkProject.controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.FileService;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl.IndexingServiceImpl;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.LoadingException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.StorageException;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.*;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.GroupRequestCreate;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostRequest;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	private final GroupService groupService;

	private final ElasticsearchOperations elasticOperations;
	private final UserService userService;
	
	private final GroupAdminService groupAdminService;

	private final SearchService searchService;

	private final ImageService imageService;

	private final PostService postService;

//	private final IndexingService indexingService;
	private final IndexingServiceImpl indexingServiceImpl;

	private final FileService fileService;
	public GroupController(GroupService groupService,UserService userService,GroupAdminService
			groupAdminService,PostService
			postService, SearchService searchService, ElasticsearchOperations elasticOperations,
						  IndexingServiceImpl indexingServiceImpl, FileService fileService, ImageService imageService) {
		this.groupAdminService = groupAdminService;
		this.groupService = groupService;
		this.userService = userService;
		this.imageService = imageService;
		this.postService = postService;
		this.searchService = searchService;
		this.elasticOperations = elasticOperations;
//		this.indexingService = indexingService;
		this.indexingServiceImpl = indexingServiceImpl;
		this.fileService = fileService;
		
	}

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Group> addGroup(
			Authentication authentication,
			@RequestParam(value = "groupDescriptionPdf", required = false) MultipartFile groupDescriptionPdf,
			@RequestPart("groupRequest") GroupRequestCreate groupRequest) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		Group newGroup = new Group();
		newGroup.setName(groupRequest.getName());
		newGroup.setDescription(groupRequest.getDescription());
		newGroup.setCreatedAt(LocalDate.now());
		newGroup.setSuspended(false);
		newGroup.setDeleted(false);
		newGroup.setSuspendedReason(null);

		Group groupCreated = groupService.addGroup(newGroup);

		GroupAdmin newGroupAdmin = new GroupAdmin(userLogged, groupCreated);
		groupAdminService.addGroupAdmin(newGroupAdmin);

		GroupIndex groupIndex = new GroupIndex();
		groupIndex.setName(newGroup.getName());
		groupIndex.setDatabaseId(newGroup.getGroupId());
		groupIndex.setDescription(newGroup.getDescription());
		groupIndex.setCreatedAt(newGroup.getCreatedAt());
		groupIndex.setPostCount(0L);
		groupIndex.setDeleted(newGroup.isDeleted());

		if (groupDescriptionPdf != null && !groupDescriptionPdf.isEmpty()) {
			try {
				String mimeType = indexingServiceImpl.detectMimeType(groupDescriptionPdf);
				System.out.println(mimeType + " mime type");
				String extractedDocumentIndex = indexingServiceImpl.extractDocumentContent(groupDescriptionPdf);
				System.out.println(extractedDocumentIndex + " extracted text from pdf");
				groupIndex.setPdfDescription(extractedDocumentIndex);
				fileService.store(groupDescriptionPdf, groupIndex.getName() + "group description pdf" + UUID.randomUUID().toString());
			} catch (StorageException | LoadingException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
		searchService.save(groupIndex);

		return new ResponseEntity<>(newGroup, HttpStatus.OK);
	}


	@GetMapping("/all")
	@ResponseBody
	public List<Group> getAllGroups(Authentication authentication) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    List<Group> groups;
	    if (userLogged.getRole().toString().equals("ADMIN")) {
	        groups = groupService.findAll();
	    } else {
	        groups = groupService.findAllUndeletedGroups();
	    }

	    return groups;
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Message deleteGrouo(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Group groupForDeletion = groupService.findOne(id);
		boolean admin = false;
//		for (GroupAdmin groupAdmin: groupForDeletion.getAdmins()) {
//			if (groupAdmin.getUser().equals(userLogged.getUsername())) {
//				admin = true;
//				break;
//			}
//			
//		}
		 if (userLogged.getRole().toString().equals("ADMIN")) {
			 admin = true;
		    }
		 if (!admin) {
		        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not admin of this app.");

		 }
		 
		 groupForDeletion.setDeleted(true);
		 
		 for (Post post: groupForDeletion.getPosts()) {
			 post.setDeleted(true);
			 
		 }
		 groupService.updateGroup(groupForDeletion);
		return new Message("Sucessfuly delete.");	
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Group> updateGroupApi(Authentication authentication,@RequestBody GroupRequestCreate group,
			@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Group oldGroup = groupService.findOne(id);
		oldGroup.setDescription(group.getDescription());
		oldGroup.setName(group.getName());
		
		
		boolean admin = false;
		for (GroupAdmin groupAdmin: oldGroup.getAdmins()) {
			if (groupAdmin.getUser().equals(userLogged.getUsername())) {
				admin = true;
				break;
			}
			
		}
		 if (userLogged.getRole().toString().equals("ADMIN")) {
			 admin = true;
		    }
		 if (!admin) {
		        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not admin of this group.");

		 }
		
		 Group updatedGroup = groupService.updateGroup(oldGroup);
		return new ResponseEntity<>(updatedGroup,HttpStatus.OK);
		
	}
	
	@PostMapping("/{groupId}/post")
	@ResponseBody
	public ResponseEntity<Post> createPostinGroup(Authentication authentication,@RequestParam(value = "postContentPdf", required = false)
										 MultipartFile postContentPdf, @RequestPart("postRequest") PostRequest post, @PathVariable("groupId") Long groupId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Group group = groupService.findOne(groupId);
		GroupIndex groupIndex = searchService.findGroupByDatabaseId(group.getGroupId());
		groupIndex.setPostCount(groupIndex.getPostCount() + 1);
		searchService.save(groupIndex);

		boolean isMemberOrAdmin = false;

		for (GroupAdmin groupAdmin : group.getAdmins()) {
			if (groupAdmin.getUser().equals(userLogged.getUsername())) {
				isMemberOrAdmin = true;
				break;
			}
		}

		if (!isMemberOrAdmin) {
			for (GroupRequest request : group.getGroupRequests()) {
				if (request.isApproved() && request.getUser().equals(userLogged.getUsername())) {
					isMemberOrAdmin = true;
					break;
				}
			}
		}

		if (!isMemberOrAdmin) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not a member of this group.");
		}
		Post newPost = new Post();
		newPost.setContent(post.getContent());
		newPost.setTitle(post.getTitle());
		newPost.setGroupPosted(group);
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
		postIndex.setGroupPosted(newPost.getGroupPosted());
		postIndex.setDeleted(newPost.isDeleted());
		postIndex.setUser(newPost.getUser());
		postIndex.setPostLikes(0L);

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

	@GetMapping("/search/byPostRange")
	public List<GroupIndex> searchGroupsByPostRange(Authentication authentication,@RequestParam(required = false) Long minPosts,
													@RequestParam(required = false) Long maxPosts) {
		User userLogged = null;
		String username = authentication.getName();

		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return searchService.findByPostCountBetween(minPosts, maxPosts);
	}

	@GetMapping("/all/elastic/{groupId}")
	public ResponseEntity<List<PostIndex>> getAllPostIndexesInGroup(Authentication authentication,
																	@PathVariable("groupId") Long groupId) {
		User userLogged = null;
		String username = authentication.getName();

		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		List<PostIndex> searchResults = searchService.getAllPostIndexesinGroup(groupId);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/{groupId}")
	public Object getGroupDetails(Authentication authentication, @PathVariable("groupId") Long groupId) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }
	    
	    Group group = groupService.findOne(groupId);

	    boolean isMemberOrAdmin = false;
	    
	    for (GroupAdmin groupAdmin : group.getAdmins()) {
	        if (groupAdmin.getUser().equals(userLogged.getUsername())  || userLogged.getRole().toString().equals("ADMIN") ) {
	            isMemberOrAdmin = true;
	            break;
	        }
	    }
	    
	    if (!isMemberOrAdmin) {
	        for (GroupRequest request : group.getGroupRequests()) {
	            if (request.isApproved() && request.getUser().equals(userLogged.getUsername())) {
	                isMemberOrAdmin = true;
	                break;
	            }
	        }
	    }
	    
	    if (isMemberOrAdmin) {
	        return new ResponseEntity<>(group, HttpStatus.OK);
	    } else {
		    return new ResponseEntity<>(new Message("You are not member or admi nof this group."), HttpStatus.UNAUTHORIZED);
	    }
	}
	
	@GetMapping("/isAdmin/{groupId}")
	public boolean checkMembership(Authentication authentication, @PathVariable("groupId") Long groupId) {
		
		String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }
	    
	    Group group = groupService.findOne(groupId);
	    
	    boolean isAdmin = false;
	    
	    if (userLogged.getRole().toString().equals("ADMIN")) {
	    	isAdmin = true;
	    	return isAdmin;
	    }
	    
	    for (GroupAdmin groupAdmin : group.getAdmins()) {
	        if (groupAdmin.getUser().equals(userLogged.getUsername())  || userLogged.getRole().toString().equals("ADMIN") ) {
	        	isAdmin = true;
	            return isAdmin;
	        }
	    }
	    return false;
		
}
	
	@GetMapping("/posts/{groupId}")
	public ResponseEntity<List<Post>> getGroupPosts(Authentication authentication, @PathVariable("groupId") Long groupId) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    Group group = groupService.findOne(groupId);
	    boolean isMemberOrAdmin = false;

	    for (GroupAdmin groupAdmin : group.getAdmins()) {
	        if (groupAdmin.getUser().equals(userLogged.getUsername()) || userLogged.getRole().toString().equals("ADMIN")) {
	            isMemberOrAdmin = true;
	            break;
	        }
	    }

	    if (!isMemberOrAdmin) {
	        for (GroupRequest request : group.getGroupRequests()) {
	            if (request.isApproved() && request.getUser().equals(userLogged.getUsername())) {
	                isMemberOrAdmin = true;
	                break;
	            }
	        }
	    }

	    if (isMemberOrAdmin) {
	        List<Post> posts = group.getPosts();
	        return new ResponseEntity<>(posts, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	}


	//TODO ELASTIC SEARCH
	@GetMapping("/searchByName")
	public ResponseEntity<List<GroupIndex>> searchGroupsByName(Authentication authentication,
															   @RequestParam String name) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		List<GroupIndex> searchResults = searchService.searchGroupsByName(name);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/searchByDescriptionOrPdfDescription")
	public ResponseEntity<List<GroupIndex>> searchGroupsByDescription(Authentication authentication,
																	  @RequestParam(required = false) String description,
																	  @RequestParam(required = false) String pdfDescription) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		List<GroupIndex> searchResults = searchService.searchGroupsByDescriptionOrPdfDescription(description, pdfDescription);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/all/elastic")
	public ResponseEntity<List<GroupIndex>> getAllGroupIndexes(
			Authentication authentication) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		List<GroupIndex> searchResults = searchService.getAllGroupIndexes();
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}
}