package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;


public class GroupJoinRequestCreate {
	
    private Long groupId;

    public GroupJoinRequestCreate() {}
    
	public GroupJoinRequestCreate(Long groupId) {
		super();
		this.groupId = groupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroup(Long groupId) {
		this.groupId = groupId;
	}

	
}
