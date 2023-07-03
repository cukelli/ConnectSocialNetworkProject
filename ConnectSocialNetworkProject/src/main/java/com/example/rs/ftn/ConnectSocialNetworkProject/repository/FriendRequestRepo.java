	package com.example.rs.ftn.ConnectSocialNetworkProject.repository;
	
	import java.util.List;

	import java.util.Optional;
	
	import org.springframework.data.domain.Page;
	import org.springframework.data.domain.Pageable;
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
	import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
	
	public interface FriendRequestRepo extends JpaRepository<FriendRequest,Long> {
		
	
		public Page<FriendRequest> findAll(Pageable pageable);
		
		FriendRequest save (FriendRequest friendRequest);
		
	    void delete(FriendRequest friendRequest);
		
		Optional<FriendRequest> findById(Long id); //optinal rukuje sa null vrednostima
		 
		FriendRequest findBySentByAndSentFor(User sentBy,User sentFor);
		
		long count();
		
	    public List<FriendRequest> findAll();
	    
		public List<FriendRequest> findBySentFor(User sentFor);
		
		public List<FriendRequest> findBySentBy(User sentFor);

		public List<FriendRequest> findAllByApprovedFalseAndSentFor(User user);
		
		public List<FriendRequest> findAllByApprovedTrueAndSentBy(User userBy);
		
		public List<FriendRequest> findAllByApprovedTrueAndSentFor(User userFor);


	
	
	
	}
