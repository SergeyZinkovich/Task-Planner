package com.taskplanner.ui;

import com.arellomobile.mvp.MvpView;
import com.taskplanner.PermissionModel;
import com.taskplanner.data.entity.PermissionEntity;

import java.util.ArrayList;

public interface PermissionFragmentView extends MvpView {
    void setPermissions(ArrayList<PermissionModel> permissions);
    public void refreshPermissions();
}
