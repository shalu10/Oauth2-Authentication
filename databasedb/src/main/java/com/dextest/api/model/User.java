package com.dextest.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Collection;



import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Users")
public class User implements UserDetails {
	

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "userId", updatable = false, nullable = false)
	private String userId;
    
	private String username;
	
	private String firstName;

    private String lastName; 
    
    private String avatar;
    
	private Boolean enabled;
	
	private String principalId;
	
		
	private LocalDateTime created;

	private LocalDateTime lastLogin;

	@Column(length = 61)
    private String password;
	
	
	 @JsonBackReference
	    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)    
	    @JoinTable(name="users_contacts",joinColumns = @JoinColumn(name="userId", referencedColumnName = "userId"),inverseJoinColumns = @JoinColumn(name = "contactId", referencedColumnName = "contactId" ))
	    private Set<Contact> contacts=new HashSet<Contact>();	
	 @JsonBackReference
	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId"))
	    private Collection<Role> roles;
	 @JsonBackReference
	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(name = "users_activations", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "activationId", referencedColumnName = "activationId"))
	    private Set<Activation> activations=new HashSet<>(0);
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
			return authorities;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		return enabled;
	}
	public Set<Activation> getActivations() {
		return activations;
	}
	public void setActivations(Set<Activation> activations) {
		this.activations = activations;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	
	
	

}
