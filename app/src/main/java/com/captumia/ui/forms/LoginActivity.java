package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.data.User;
import com.captumia.ui.RequestManagerActivity;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.ProgressDialogRequestListener;
import com.utilsframework.android.view.Toasts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends RequestManagerActivity {
    @BindView(R.id.login_edit_text)
    EditText loginView;
    @BindView(R.id.password_edit_text)
    EditText passwordView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        final String login = loginView.getText().toString();
        if (login.isEmpty()) {
            Toasts.toast(this, R.string.empty_login);
            return;
        }

        final String password = passwordView.getText().toString();
        if (password.isEmpty()) {
            Toasts.toast(this, R.string.empty_password);
            return;
        }

        ProgressDialogRequestListener<User, Throwable> listener =
                new ProgressDialogRequestListener<User, Throwable>(this, R.string.logging_loading) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onLoginFailed(e);
                    }

                    @Override
                    public void onSuccess(User user) {
                        onUserIsReady(user);
                    }
                };
        Call<User> call = getRestApiClient().login(login, password);
        getRequestManager().executeCall(call, listener, CancelStrategy.INTERRUPT);
    }

    private void onUserIsReady(User user) {
        CaptumiaApplication.getInstance().login();
        Toasts.toast(this, R.string.hello_user_toast, user.getDisplayName());
        finish();
    }

    private void onLoginFailed(Throwable e) {
        Toasts.toast(this, e.getMessage());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
