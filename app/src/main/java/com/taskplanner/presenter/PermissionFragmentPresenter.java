package com.taskplanner.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.PermissionModel;
import com.taskplanner.data.PermissionManager;
import com.taskplanner.data.entity.PermissionEntity;
import com.taskplanner.ui.PermissionFragmentView;
import com.taskplanner.ui.custom_views.PermissionButton;

import java.util.ArrayList;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PermissionFragmentPresenter extends MvpPresenter<PermissionFragmentView>
        implements PermissionManager.GetPermissionCallback, View.OnClickListener, PermissionManager.RequestPermissionCallback {

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

    @Override
    public void getPermissionFailed() {
        router.showSystemMessage("Get permission failed");
    }

    //Delete buttons click
    @Override
    public void onClick(View v) {
        PermissionManager.getInstance().
                deletePermission(((PermissionButton) v).getPermissionModel().getId(), this);
    }

    @Override
    public void requestPermissionSuccess(boolean success) {
        if (success) {
            router.showSystemMessage("Successfully deleted");
            getViewState().refreshPermissions();
        }
        else {
            router.showSystemMessage("Deletion faild");
        }
    }
}
