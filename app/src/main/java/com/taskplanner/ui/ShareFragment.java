package com.taskplanner.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.ShareFragmentPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class ShareFragment extends MvpAppCompatFragment implements ShareFragmentView {

    @BindView(R.id.cbReadPermission)
    CheckBox cbReadPermission;
    @BindView(R.id.cbUpdatePermission)
    CheckBox cbUpdatePermission;
    @BindView(R.id.cbDeletePermission)
    CheckBox cbDeletePermission;
    @BindView(R.id.llPermissionToken)
    LinearLayout llPermissionToken;
    @BindView(R.id.createPermissionLabel)
    TextView createPermissionLabel;
    @BindView(R.id.tvPermissionToken)
    TextView tvPermissionToken;

    @Inject
    Router router;

    @InjectPresenter
    ShareFragmentPresenter shareFragmentPresenter;

    private String token;

    @ProvidePresenter
    ShareFragmentPresenter provideShareFragmentPresenter(){
        return new ShareFragmentPresenter(router, (EventModel) getArguments().getParcelable("event"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.share_fragment, container, false);
        ButterKnife.bind(this, view);

        llPermissionToken.setVisibility(View.GONE);
        if(shareFragmentPresenter.getEvent() != null){
            createPermissionLabel.setText(R.string.create_permission_for_the_event_label);
        }
        else {
            createPermissionLabel.setText(R.string.create_permission_for_all_event_label);
        }
        return view;
    }

    public void setToken(String token){
        llPermissionToken.setVisibility(View.VISIBLE);
        tvPermissionToken.setText(token);
    }

    @OnClick(R.id.bCreateShareToken)
    public void onCreateShareTokenButtonClick(){
        if (cbReadPermission.isChecked() || cbUpdatePermission.isChecked() || cbDeletePermission.isChecked()) {
            shareFragmentPresenter.createShareToken(new boolean[]{cbReadPermission.isChecked(),
                    cbUpdatePermission.isChecked(), cbDeletePermission.isChecked()});
        }
    }

    @OnClick(R.id.copyButton)
    public void onCopyButtonClick(){
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Share token", tvPermissionToken.getText().toString());
        clipboard.setPrimaryClip(clip);
        router.showSystemMessage("Copied");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                router.exit();
                return true;
            case R.id.activateToken:
                activateShareToken();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void activateShareToken(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter token");
        EditText input = new EditText(getContext());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                token = input.getText().toString();
                shareFragmentPresenter.activateShareToken(token);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
