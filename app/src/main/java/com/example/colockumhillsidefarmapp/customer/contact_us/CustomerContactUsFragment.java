package com.example.colockumhillsidefarmapp.customer.contact_us;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colockumhillsidefarmapp.R;
import com.mailchimp.sdk.api.model.Contact;
import com.mailchimp.sdk.api.model.ContactStatus;
import com.mailchimp.sdk.main.Mailchimp;

public class CustomerContactUsFragment extends Fragment {

    private Button btnSignUpForNewsletter;
    private EditText txtEmail, txtFirstName, txtLastName;
    private TextView farmEmailLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_contact_us, container, false);

        initVariables(root);

        TextView feedback = root.findViewById(R.id.FarmEmailLink);
        feedback.setText(Html.fromHtml("<a href=\"mailto:suzanne@colockumhillsidefarm.com\">Email Us!</a>"));
        feedback.setMovementMethod(LinkMovementMethod.getInstance());
        feedback.setLinkTextColor(Color.BLUE);

        btnSignUpForNewsletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();

                if (validateInput()) {
                    Contact newContact = new Contact.Builder(email)
                            .setMergeField("FNAME", firstName)
                            .setMergeField("LNAME", lastName)
                            .setContactStatus(ContactStatus.SUBSCRIBED)
                            .addTag("Power User")
                            .build();
                    Mailchimp sdk = Mailchimp.sharedInstance();
                    sdk.createOrUpdateContact(newContact);
                    Toast.makeText(view.getContext(), email + " added to our list!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please enter valid data.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private boolean validateInput () {
        return true;
    }

    private void initVariables (View root) {
        btnSignUpForNewsletter = root.findViewById(R.id.btnSignUpForNewsletter);
        txtEmail = root.findViewById(R.id.editTextTextEmailAddress);
        txtFirstName = root.findViewById(R.id.editTextFirstName);
        txtLastName = root.findViewById(R.id.editTextLastName);
    }
}
