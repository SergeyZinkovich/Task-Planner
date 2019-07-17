package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.R;
import com.taskplanner.presenter.PermissionFragmentPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class PermissionFragment extends MvpAppCompatFragment implements PermissionFragmentView {

    @Inject
    Router router;

    @InjectPresenter
    PermissionFragmentPresenter permissionFragmentPresenter;

    @ProvidePresenter
    PermissionFragmentPresenter providePermissionFragmentPresenter(){
        return new PermissionFragmentPresenter(router);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
