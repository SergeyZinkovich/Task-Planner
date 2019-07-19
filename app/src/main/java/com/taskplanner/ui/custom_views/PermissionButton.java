package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taskplanner.PermissionModel;

public class PermissionButton extends AppCompatButton {

    private PermissionModel permissionModel;

    public PermissionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PermissionModel getPermissionModel() {
        return permissionModel;
    }

    public void setPermissionModel(PermissionModel permissionModel) {
        this.permissionModel = permissionModel;
    }
}
