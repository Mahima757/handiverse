package com.model;

public class User {
	  private int    userId;
	    private String fullName;
	    private String email;
	    private String password;    // Stored as BCrypt hash
	    private int    roleId;
	    private String roleName;    // Joined from Role table (not a DB column)
	 
	    // ─── Constructors ─────────────────────────────────────────────────────────
	 
	    public User() {}
	 
	    public User(int userId, String fullName, String email, String password, int roleId) {
	        this.userId   = userId;
	        this.fullName = fullName;
	        this.email    = email;
	        this.password = password;
	        this.roleId   = roleId;
	    }
	 
	    // ─── Getters & Setters ────────────────────────────────────────────────────
	 
	    public int getUserId() {
	        return userId;
	    }
	 
	    public void setUserId(int userId) {
	        this.userId = userId;
	    }
	 
	    public String getFullName() {
	        return fullName;
	    }
	 
	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }
	 
	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
	 
	    public String getPassword() {
	        return password;
	    }
	 
	    public void setPassword(String password) {
	        this.password = password;
	    }
	 
	    public int getRoleId() {
	        return roleId;
	    }
	 
	    public void setRoleId(int roleId) {
	        this.roleId = roleId;
	    }
	 
	    public String getRoleName() {
	        return roleName;
	    }
	 
	    public void setRoleName(String roleName) {
	        this.roleName = roleName;
	    }
	 
	    // ─── Convenience Helpers ─────────────────────────────────────────────────
	 
	    /**
	     * Returns true if this user has the Admin role.
	     */
	    public boolean isAdmin() {
	        return "Admin".equalsIgnoreCase(roleName);
	    }
	 
	    @Override
	    public String toString() {
	        return "User{userId=" + userId
	             + ", fullName='" + fullName + "'"
	             + ", email='"    + email    + "'"
	             + ", roleId="    + roleId   + "}";
	    }
}
