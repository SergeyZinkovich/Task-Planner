package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskplanner.PermissionModel;
import com.taskplanner.R;
import com.taskplanner.presenter.PermissionFragmentPresenter;
import com.taskplanner.ui.custom_views.PermissionButton;

import java.util.ArrayList;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {

    private ArrayList<PermissionModel> permissions;
    private PermissionFragmentPresenter presenter;

    public PermissionAdapter(PermissionFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    public void setPermissions(ArrayList<PermissionModel> permissions) {
        this.permissions = permissions;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.permission_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvPermissionType.setText(permissions.get(i).getPermissionType());
        viewHolder.tvUserName.setText(permissions.get(i).getUser());
        viewHolder.eventName.setText(permissions.get(i).getEventName());
        viewHolder.button.setPermissionModel(permissions.get(i));
        viewHolder.button.setOnClickListener(presenter);
    }

    @Override
    public int getItemCount() {
        if (permissions != null) {
            return permissions.size();
        }
        else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvPermissionType;
        final TextView tvUserName;
        final TextView eventName;
        final PermissionButton button;

        ViewHolder(View view){
            super(view);
            tvPermissionType = (TextView) view.findViewById(R.id.permissionType);
            tvUserName = (TextView) view.findViewById(R.id.userName);
            eventName = (TextView) view.findViewById(R.id.eventName);
            button = (PermissionButton) view.findViewById(R.id.bDelete);
        }
    }

}
