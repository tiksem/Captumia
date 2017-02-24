package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.captumia.CaptumiaApplication;
import com.captumia.R;
import com.captumia.data.TokenRequestData;
import com.captumia.ui.RequestManagerActivity;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.ProgressDialogRequestListener;
import com.utilsframework.android.view.Toasts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends RequestManagerActivity {
    @BindView(R.id.login_edit_text)
    EditText loginView;
    @BindView(R.id.password_edit_text)
    EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        String login = loginView.getText().toString();
        if (login.isEmpty()) {
            Toasts.toast(this, R.string.empty_login);
            return;
        }

        String password = passwordView.getText().toString();
        if (password.isEmpty()) {
            Toasts.toast(this, R.string.empty_password);
            return;
        }

        ProgressDialogRequestListener<TokenRequestData, Throwable> listener =
                new ProgressDialogRequestListener<TokenRequestData, Throwable>(this, R.string.logging_loading) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onLoginFailed(e);
                    }

                    @Override
                    public void onSuccess(TokenRequestData tokenRequestData) {
                        super.onSuccess(tokenRequestData);
                        onLoginSuccess(tokenRequestData);
                    }
                };
        getRequestManager().executeCall(getRestApiClient().getToken(login, password), listener,
                CancelStrategy.INTERRUPT);
    }

    private void onLoginSuccess(TokenRequestData data) {
        CaptumiaApplication.getInstance().login(data.getToken());
        Toasts.toast(this, R.string.hello_user_toast, data.getUserDisplayName());
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
