package com.thecodearc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.startup.AppInitializer;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import app.rive.runtime.kotlin.RiveAnimationView;
import app.rive.runtime.kotlin.core.Rive;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    RiveAnimationView riveAnimationView;
    TextInputLayout emailLayout,passwordLayout;
    AppCompatButton loginbtn;
    CardView cardView;
    String emailText,passwordText;
    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emailtext);
        cardView = findViewById(R.id.rive_char_card);
        password = findViewById(R.id.passtext);
        loginbtn = findViewById(R.id.loginbtn);
        emailLayout = findViewById(R.id.outlinedTextField);
        passwordLayout = findViewById(R.id.passoutline);
        riveAnimationView = findViewById(R.id.rive_char);
        rootLayout = findViewById(R.id.rootLayout);
        Rive.INSTANCE.init(getApplicationContext());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        }

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                InputFilter noSuggestionsFilter = new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        // Simply return the source without any modifications to prevent suggestions
                        return source;
                    }
                };

                email.setFilters(new InputFilter[]{noSuggestionsFilter});

                email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                if (b){

                    riveAnimationView.getController()
                            .setBooleanState(
                                    "Login Machine",
                                    "isChecking",
                                    true);

                }else {

                    riveAnimationView
                            .getController()
                            .setBooleanState(
                            "Login Machine",
                            "isChecking",
                            false);

                }

            }
        });




        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                riveAnimationView.getController().setNumberState("Login Machine", "numLook", editable.length()*5);
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b){

                    riveAnimationView.getController().setBooleanState("Login Machine", "isHandsUp", true);
                    rootLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Clear focus from any focused view, like an EditText
                            View focusedView = getCurrentFocus();
                            if (focusedView != null) {
                                focusedView.clearFocus();
                            }
                        }
                    });

                }else {

                    riveAnimationView.getController().setBooleanState("Login Machine", "isHandsUp", false);

                }

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginbtn.setBackground(getDrawable(R.drawable.loginbtnhover));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    loginbtn.setTextColor(getColor(R.color.white));
                }

                emailText = email.getText().toString();
                passwordText = password.getText().toString();


                if (emailText.isEmpty() ){

                    email.setError("Enter valid email");
                    riveAnimationView.getController().fireState("Login Machine", "trigFail");


                } else if (passwordText.isEmpty()) {

                    password.setError("Enter valid password");
                    riveAnimationView.getController().fireState("Login Machine", "trigFail");
                }

                else {
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                    riveAnimationView.getController().fireState("Login Machine", "trigSuccess");


                }

            }
        });

    }
}