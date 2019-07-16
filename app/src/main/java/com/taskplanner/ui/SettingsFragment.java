package com.taskplanner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.SettingsFragmentPresenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class SettingsFragment extends MvpAppCompatFragment implements SettingsFragmentView {

    @BindView(R.id.ivUserAvatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.authButton)
    Button authButton;
    @BindView(R.id.cvUserAvatar)
    CardView cvUserAvatar;

    @InjectPresenter
    SettingsFragmentPresenter settingsFragmentPresenter;

    @Inject
    Router router;

    private static int RC_SIGN_IN = 9001; //Todo: вынести

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        setAuthViews();
        return view;
    }

    private void setAuthViews(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            tvEmail.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.VISIBLE);
            cvUserAvatar.setVisibility(View.VISIBLE);
            tvName.setText(user.getDisplayName());
            tvEmail.setText(user.getEmail());
            Picasso.with(getContext())
                    .load(user.getPhotoUrl())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivUserAvatar);
            authButton.setText(R.string.logout_button_text);
        }
        else{
            tvEmail.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
            cvUserAvatar.setVisibility(View.GONE);
            authButton.setText(R.string.login_button_text);
        }
    }

    @OnClick(R.id.authButton)
    public void onLogoutButtonClick(TextView textView){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            AuthUI.getInstance()
                    .signOut(getContext())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            setAuthViews();
                        }
                    });
        }
        else {
            LogIn();
        }
    }

    private void LogIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        //.setLogo(R.drawable.my_great_logo) //TODO: добавить пикчу
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            setAuthViews();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            router.exit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
