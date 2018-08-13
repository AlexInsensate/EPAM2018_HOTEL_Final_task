package ua.nure.churkin.Hotel.db;

import ua.nure.churkin.Hotel.db.entity.User;


public enum Role {
	NOONE, ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
