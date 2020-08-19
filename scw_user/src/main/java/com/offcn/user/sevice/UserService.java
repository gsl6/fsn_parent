package com.offcn.user.sevice;

import com.offcn.user.po.TMember;

public interface UserService {
	 void registerUser(TMember member);

	 TMember login(String username,String password);
}
