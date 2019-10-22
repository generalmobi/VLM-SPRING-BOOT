package com.vlm.ui.support;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static boolean isGranted(String c_authority) {
        if (null == c_authority || "".equals(c_authority)) {
            return false;
        }
        final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
        for  (GrantedAuthority authority :  granted)
        {
        	if (authority.getAuthority().equalsIgnoreCase(c_authority))
        	{
        		return true;
        	}
        }
        return false;
    }
 
    public static String getUser() 
    {
    	if (SecurityContextHolder.getContext().getAuthentication()!=null)
    	{
    	   UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	      String name = user.getUsername(); //get logged in username
    	      
    	      return name;
    	}
    	return "anonymous";
    }
    
    private static Collection<? extends GrantedAuthority> getPrincipalAuthorities() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (null == currentUser) {
            return Collections.emptyList();
        }
        if ((null == currentUser.getAuthorities()) || (currentUser.getAuthorities().isEmpty())) {
            return Collections.emptyList();
        }
        Collection<? extends GrantedAuthority> granted = currentUser.getAuthorities();
        return granted;
    }

}
