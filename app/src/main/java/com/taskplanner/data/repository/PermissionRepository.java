package com.taskplanner.data.repository;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.data.entity.PermissionResponseEntity;
import com.taskplanner.data.entity.UserResponseEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PermissionRepository {

    @Inject
    TaskPlannerApi taskPlannerApi;

    public PermissionRepository(){
        App.getComponent().inject(this);
    }

    public Single<PermissionResponseEntity> getPermissions(String entityType, boolean mine){
        return taskPlannerApi.getPermissions(entityType, mine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBody> createPermissionToken(PermissionRequestEntity[] permissions){
        return taskPlannerApi.createPermissionToken(permissions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<PermissionResponseEntity> activatePermissionToken(String token){
        return taskPlannerApi.activatePermissionToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<PermissionResponseEntity> deletePermission(Long id){
        return taskPlannerApi.deletePermission(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<UserResponseEntity> getUser(String[] userIds){
        return taskPlannerApi.getUser(userIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
