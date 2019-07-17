package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.ui.PermissionFragmentView;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PermissionFragmentPresenter extends MvpPresenter<PermissionFragmentView> {

    private Router router;

    public PermissionFragmentPresenter(Router router) {
        this.router = router;
    }
}
