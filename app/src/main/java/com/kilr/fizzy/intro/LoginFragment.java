package com.kilr.fizzy.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kilr.fizzy.R;

import timber.log.Timber;

/**
 * Created by idanakav on 9/3/15.
 */
public class LoginFragment extends Fragment {

    private CallbackManager mFacebookCallback;
    private LoginButton mLoginButton;
    private TextView mWelcomeText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        //PrefUtils.markOnBoardingDone(this);
        mFacebookCallback = CallbackManager.Factory.create();

        mLoginButton = (LoginButton) v.findViewById(R.id.login_button);
        mLoginButton.setFragment(this);
        mWelcomeText = (TextView) v.findViewById(R.id.welcome);

        LoginManager.getInstance().logOut();
        mLoginButton.registerCallback(mFacebookCallback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.i("Facebook accesstoken " + loginResult.getAccessToken());
                mWelcomeText.setText("Welcome " + Profile.getCurrentProfile().getName());
            }

            @Override
            public void onCancel() {
                Timber.i("Facebook canceld");

            }

            @Override
            public void onError(FacebookException e) {
                Timber.i("Facebook error " + e.getMessage());
            }
        });

        return v;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallback.onActivityResult(requestCode,resultCode,data);
    }

}
