package com.example.rs.ftn.ConnectSocialNetworkProject.controller;
import java.time.LocalDate;
import java.util.List;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.GroupRequestCreate;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupAdminService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	private final GroupService groupService;

	private ElasticsearchOperations elasticOperations;
	
	private final UserService userService;
	
	private final GroupAdminService groupAdminService;

	private final SearchService searchService;


	private final PostService postService;
	
	public GroupController(GroupService groupService,UserService userService,GroupAdminService
			groupAdminService,PostService
			postService, SearchService searchService, ElasticsearchOperations elasticOperations) {
		this.groupAdminService = groupAdminService;
		this.groupService = groupService;
		this.userService = userService;
		this.postService = postService;
		this.searchService = searchService;
		this.elasticOperations = elasticOperations;
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Group> addGroup(Authentication authentication,@RequestBody GroupRequestCreate group) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Group newGroup = new Group();
		newGroup.setName(group.getName());
		newGroup.setDescription(group.getDescription());
		newGroup.setCreatedAt(LocalDate.now());
		newGroup.setSuspended(false);
		newGroup.setDeleted(false);
		newGroup.setSuspendedReason(null);
		Group groupCreated = groupService.addGroup(newGroup);
		GroupAdmin newGroupAdmin = new GroupAdmin(userLogged,groupCreated);
		groupAdminService.addGroupAdmin(newGroupAdmin);
		GroupIndex groupIndex = new GroupIndex();
		groupIndex.setName(newGroup.getName());
		groupIndex.setDescription(newGroup.getDescription());
		groupIndex.setCreatedAt(newGroup.getCreatedAt());
		groupIndex.setDeleted(newGroup.isDeleted());
		searchService.save(groupIndex);
		return new ResponseEntity<>(newGroup,HttpStatus.OK);
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
	public Message createPostinGroup(Authentication authentication, @RequestBody PostRequest post,
	        @PathVariable("groupId") Long groupId) {
		
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
		newPost.setCreationDate(post.getCreationDate());
		newPost.setGroupPosted(group);
		newPost.setUser(userLogged);
		
		postService.addPost(newPost);
		return new Message("Post added succesfuly.");
		
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
	public ResponseEntity<Page<GroupIndex>> searchGroupsByName(Authentication authentication,
															   @RequestParam String name,
															   @RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "10") int size) {

		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Page<GroupIndex> searchResults = searchService.searchGroupsByName(name, PageRequest.of(page, size));
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@GetMapping("/searchByDescription")
	public ResponseEntity<Page<GroupIndex>> searchGroupsByDescription(@RequestParam String description,
																	  @RequestParam(defaultValue = "0") int page,
																	  @RequestParam(defaultValue = "10") int size) {
		Page<GroupIndex> searchResults = searchService.searchGroupsByDescription(description, PageRequest.of(page, size));
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}


}