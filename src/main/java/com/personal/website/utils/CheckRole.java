package com.personal.website.utils;

import com.personal.website.entity.RoleEntinty;
import com.personal.website.model.ERole;

import java.util.List;

public class CheckRole
{
    public static boolean isAdmin(List<RoleEntinty> roles)
    {
        boolean flag = false;

        for(int i = 0; i <roles.size(); i++)
        {
            if(roles.get(i).getName().name().equals(ERole.ROLE_ADMIN.name()))
                flag = true;
        }
        return roles.size()>1 && flag;
    }
    public static boolean isSubscriber(List<RoleEntinty> roles)
    {
        return roles.size() == 1;
    }
}
