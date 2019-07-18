package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.PermissionModel;
import com.taskplanner.data.PermissionManager;
import com.taskplanner.data.entity.PermissionEntity;
import com.taskplanner.ui.PermissionFragmentView;

import java.util.ArrayList;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PermissionFragmentPresenter extends MvpPresenter<PermissionFragmentView> implements PermissionManager.GetPermissionCallback {

    private Router router;

    public PermissionFragmentPresenter(Router router) {
        this.router = router;
    }

    public void getPermissions(boolean mine){
        PermissionManager.getInstance().getPermissions("EVENT", mine, this);
    }

    @Override
    public void setPermissions(ArrayList<PermissionModel> permissions) {
        getViewState().setPermissions(permissions);
    }
}
