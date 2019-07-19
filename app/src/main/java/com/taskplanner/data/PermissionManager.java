package com.taskplanner.data;

import android.util.Log;

import com.taskplanner.PermissionModel;
import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.PermissionEntity;
import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.data.entity.UserEntity;
import com.taskplanner.data.repository.EventRepository;
import com.taskplanner.data.repository.PermissionRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionManager {

    public interface CreatePermissionCallback {
        void setPermissionToken(String token);
    }

    public interface RequestPermissionCallback {
        void requestPermissionSuccess(boolean success);
    }

    public interface GetPermissionCallback{
        void setPermissions(ArrayList<PermissionModel> permissions);
    }

    private PermissionRepository permissionRepository;
    private EventRepository eventRepository;

    private static final PermissionManager INSTANCE = new PermissionManager();

    public static PermissionManager getInstance(){
        return INSTANCE;
    }

    private PermissionManager(){
        permissionRepository = new PermissionRepository();
        eventRepository = new EventRepository();
    }

    public void createPermission(PermissionRequestEntity[] permissions, CreatePermissionCallback callback){
        permissionRepository.createPermissionToken(permissions).subscribe(
                response -> callback.setPermissionToken(getTokenFromUrl(response.string())),
                throwable -> Log.e("Network error in create permission:", throwable.getMessage())
                );
    }

    private String getTokenFromUrl(String url){
        String[] s = url.split("/");
        return s[s.length - 1];
    }

    public void activateToken(String token, RequestPermissionCallback callback){
        permissionRepository.activatePermissionToken(token).subscribe(
                response -> callback.requestPermissionSuccess(true),
                throwable -> callback.requestPermissionSuccess(false)
                );
    }

    public void getPermissions(String entityType, boolean mine, GetPermissionCallback callback){
        permissionRepository.getPermissions(entityType, mine).subscribe(
                response -> getEvents(convertPermissionEntityToModel(response.getData(), mine), callback),
                throwable -> Log.e("Network error in get permission:", throwable.getMessage())
                );
    }

    private ArrayList<PermissionModel> convertPermissionEntityToModel(PermissionEntity[] permissions, boolean mine){
        ArrayList<PermissionModel> ans = new ArrayList<>();
        for (PermissionEntity permission : permissions){
            PermissionModel model = new PermissionModel();
            model.setEventName(permission.getEntityId());
            if (mine){
                model.setUser(permission.getUserId());
            }
            else {
                model.setUser(permission.getOwnerId());
            }
            model.setId(permission.getId());
            model.setPermissionType(permission.getName().split("_")[0]);
            ans.add(model);
        }
        return ans;
    }

    private void getEvents(ArrayList<PermissionModel> permissions, GetPermissionCallback callback){
        ArrayList<Long> ids = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++){
            if (permissions.get(i).getEventName().matches("[0-9]+")) {
                ids.add(Long.valueOf(permissions.get(i).getEventName()));
                permissions.get(i).setEventId(ids.get(ids.size() - 1));
            }
            else {
                permissions.get(i).setEventName("All events");
            }
        }
        if(ids.size() == 0){
            callback.setPermissions(permissions);
            return;
        }
        Long[] arrId = new Long[ids.size()];
        ids.toArray(arrId);
        eventRepository.getEventsByIds(arrId).subscribe(
                response -> getUser(setEventNameToModel(permissions, response.getData()), callback),
                throwable -> Log.e("Network error in get permission(event name):", throwable.getMessage())
                );
    }

    private ArrayList<PermissionModel> setEventNameToModel(ArrayList<PermissionModel> permissions, EventEntity[] events){
        HashMap<Long, EventEntity> eventsMap= new HashMap<>();
        for(EventEntity event : events){
            eventsMap.put(event.getId(), event);
        }
        for(PermissionModel permission : permissions){
            if (!permission.getEventName().equals("All events")) {
                permission.setEventName(eventsMap.get(permission.getEventId()).getName());
            }
        }
        return permissions;
    }

    private void getUser(ArrayList<PermissionModel> permissions, GetPermissionCallback callback){
        ArrayList<String> ids = new ArrayList<>();
        for(PermissionModel permission : permissions){
            ids.add(permission.getUser());
        }
        String[] arrIds = new String[ids.size()];
        ids.toArray(arrIds);
        permissionRepository.getUser(arrIds).subscribe(
                response -> callback.setPermissions(setUserNameToPermissionModel(permissions, response.getData())),
                throwable -> Log.e("Network error in get permission(event name):", throwable.getMessage())
        );
    }

    private ArrayList<PermissionModel> setUserNameToPermissionModel(ArrayList<PermissionModel> permissions, UserEntity[] users){
        HashMap<String, UserEntity> usersMap = new HashMap<>();
        for (UserEntity user : users){
            usersMap.put(user.getId(), user);
        }
        for (PermissionModel permission : permissions){
            permission.setUser(usersMap.get(permission.getUser()).getUsername());
        }
        return permissions;
    }

    public void deletePermission(Long id, RequestPermissionCallback callback){
        permissionRepository.deletePermission(id).subscribe(
                response -> callback.requestPermissionSuccess(true),
                throwable -> {
                    Log.e("Network error in delete permission:", throwable.getMessage());
                    callback.requestPermissionSuccess(false);
                });
    }
}
