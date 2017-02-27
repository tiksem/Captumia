package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.captumia.CaptumiaApplication;
import com.captumia.R;
import com.captumia.data.Nonce;
import com.captumia.data.User;
import com.captumia.ui.RequestManagerActivity;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.ProgressDialogRequestListener;
import com.utilsframework.android.network.RequestListener;
import com.utilsframework.android.network.retrofit.CallProvider;
import com.utilsframework.android.view.Toasts;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

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

        CallProvider<User> loginCall = new CallProvider<User>() {
            @Override
            public Call<User> getCall() {
                return getRestApiClient().login(login, password);
            }

            @Override
            public void onSuccess(User result) {
                onUserIsReady(result);
            }
        };
        CallProvider<Nonce> getNonceCall = new CallProvider<Nonce>() {
            @Override
            public Call<Nonce> getCall() {
                return getRestApiClient().getNonce();
            }

            @Override
            public void onSuccess(Nonce result) {
                onNonceRetrieved(result.nonce);
            }
        };
        List<CallProvider> calls = Arrays.<CallProvider>asList(loginCall, getNonceCall);

        ProgressDialogRequestListener<List, Throwable> listener =
                new ProgressDialogRequestListener<List, Throwable>(this, R.string.logging_loading) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onLoginFailed(e);
                    }
                };
        getRequestManager().executeMultipleCalls(calls, listener, CancelStrategy.INTERRUPT);
    }

    private void onUserIsReady(User user) {
        this.user = user;
    }

    private void onNonceRetrieved(String nonce) {
        CaptumiaApplication.getInstance().login(nonce);
        Toasts.toast(this, R.string.hello_user_toast, "Ivan");
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
