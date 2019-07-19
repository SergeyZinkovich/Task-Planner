package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.App;
import com.taskplanner.PermissionModel;
import com.taskplanner.R;
import com.taskplanner.presenter.PermissionFragmentPresenter;
import com.taskplanner.ui.adapter.PermissionAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class PermissionFragment extends MvpAppCompatFragment implements PermissionFragmentView {

    @BindView(R.id.rvPermissions)
    RecyclerView rvPermissions;

    @Inject
    Router router;

    @InjectPresenter
    PermissionFragmentPresenter permissionFragmentPresenter;

    @ProvidePresenter
    PermissionFragmentPresenter providePermissionFragmentPresenter(){
        return new PermissionFragmentPresenter(router);
    }

    private PermissionAdapter permissionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean mine = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        permissionAdapter = new PermissionAdapter(permissionFragmentPresenter);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        refreshPermissions();
    }

    public void refreshPermissions(){
        permissionFragmentPresenter.getPermissions(mine);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_fragment, container, false);
        ButterKnife.bind(this, view);
        rvPermissions.setAdapter(permissionAdapter);
        layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        rvPermissions.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void setPermissions(ArrayList<PermissionModel> permissions) {
        permissionAdapter.setPermissions(permissions);
        permissionAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                router.exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
