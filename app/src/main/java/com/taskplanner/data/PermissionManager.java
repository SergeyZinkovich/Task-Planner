package com.taskplanner.data;

import android.util.Log;

import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.data.repository.PermissionRepository;

public class PermissionManager {

    public interface CreatePermissionCallback {
        void setPermissionToken(String token);
    }

    public interface ActivatePermissionTokenCallback{
        void tokenActivateSuccess(boolean success);
    }

    private PermissionRepository permissionRepository;

    private static final PermissionManager INSTANCE = new PermissionManager();

    public static PermissionManager getInstance(){
        return INSTANCE;
    }

    private PermissionManager(){
        permissionRepository = new PermissionRepository();
    }

    public void createPermission(PermissionRequestEntity[] permissions, CreatePermissionCallback callback){
        permissionRepository.createPermissionToken(permissions)
                .subscribe(response -> callback.setPermissionToken(getTokenFromUrl(response.string())),
                        throwable -> Log.e("Network error in create permission:", throwable.getMessage())
                );
    }

    private String getTokenFromUrl(String url){
        String[] s = url.split("/");
        return s[s.length - 1];
    }

    public void activateToken(String token, ActivatePermissionTokenCallback callback){
        permissionRepository.activatePermissionToken(token)
                .subscribe(response -> callback.tokenActivateSuccess(true),
                        throwable -> callback.tokenActivateSuccess(false)
                );
    }
}
