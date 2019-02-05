package com.runninglight.tickettoride.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.runninglight.shared.LoginInfo;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ServerInfo;
import com.runninglight.tickettoride.presenter.Login_Presenter;

public class LoginFragment extends Fragment {

private EditText portNumber_ET;
private EditText hostName_ET;
private EditText userName_ET;
private EditText password_ET;
private Button login_BTN;
private Button register_BTN;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        portNumber_ET = v.findViewById(R.id.port_editText);
        hostName_ET = v.findViewById(R.id.host_editText);
        userName_ET = v.findViewById(R.id.userName_editText);
        password_ET = v.findViewById(R.id.password_editText);

        login_BTN = v.findViewById(R.id.login_button);
        register_BTN = v.findViewById(R.id.register_button);

        login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add presenter and use presenter to act.

                int port = Integer.parseInt(portNumber_ET.getText().toString());
                String host = hostName_ET.getText().toString();
                String userName = userName_ET.getText().toString();
                String password = password_ET.getText().toString();

                ServerInfo serverInfo = new ServerInfo(host, port);
                LoginInfo loginInfo = new LoginInfo(userName, password);

                Login_Presenter login_presenter = new Login_Presenter();
                login_presenter.login(loginInfo, serverInfo);
            }
        });
        register_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add presenter and use presenter to act.

                int port = Integer.parseInt(portNumber_ET.getText().toString());
                String host = hostName_ET.getText().toString();
                String userName = userName_ET.getText().toString();
                String password = password_ET.getText().toString();

                ServerInfo serverInfo = new ServerInfo(host, port);
                LoginInfo loginInfo = new LoginInfo(userName, password);

                Login_Presenter login_presenter = new Login_Presenter();
                login_presenter.register(loginInfo, serverInfo);
            }
        });


        return v;
    }


}
