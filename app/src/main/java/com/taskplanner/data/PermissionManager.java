package com.taskplanner.data;

import android.util.Log;

import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.data.repository.PermissionRepository;

import java.util.ArrayList;

public class PermissionManager {

    public interface createPermissionCallback{
        void setPermissionToken(String token);
    }

    private PermissionRepository permissionRepository;

    private static final PermissionManager INSTANCE = new PermissionManager();

    public static PermissionManager getInstance(){
        return INSTANCE;
    }

    private PermissionManager(){
        permissionRepository = new PermissionRepository();
    }

    public void createPermission(PermissionRequestEntity[] permissions, createPermissionCallback callback){
        permissionRepository.createPermissionToken(permissions)
                .subscribe(response -> callback.setPermissionToken(getTokenFromUrl(response.string())),
                        throwable -> Log.e("Network error in create permission:", throwable.getMessage())
                );
    }

    private String getTokenFromUrl(String url){
        String[] s = url.split("/");
        return s[s.length - 1];
    }
}
