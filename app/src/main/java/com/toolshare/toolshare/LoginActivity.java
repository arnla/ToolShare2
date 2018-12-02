package com.toolshare.toolshare;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolSchedule;
import com.toolshare.toolshare.models.ToolType;
import com.toolshare.toolshare.models.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.toolshare.toolshare.models.Request.addRequest;
import static com.toolshare.toolshare.models.ToolSchedule.insertToolSchedule;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Database
    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHandler(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
               startActivity(i);
           }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mSeedDataButton = (Button) findViewById(R.id.seed_button);
        mSeedDataButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               seedData();
           }
        });

        // Uncomment to add/delete tool types
/*        Button mNewToolTypeButton = (Button) findViewById(R.id.new_tool_type_button);
        mNewToolTypeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //add
                ToolType toolType = new ToolType(db, "Saws", "Cutting tools");
                toolType.addToolType(db);
                Toast.makeText(LoginActivity.this, "Tool type added", Toast.LENGTH_LONG).show();

                //delete
                ToolType.deleteToolType(db);
                Toast.makeText(LoginActivity.this, "Tool type deleted", Toast.LENGTH_LONG).show();
            }
        });*/

        // Uncomment to add/delete brands
/*        Button mNewBrandButton = (Button) findViewById(R.id.new_brand_button);
        mNewBrandButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //add
                Brand brand = new Brand("Dewalt");
                brand.addBrand(db);
                Toast.makeText(LoginActivity.this, "Brand added", Toast.LENGTH_LONG).show();

                //delete
*//*                ToolType.deleteToolType(db);
                Toast.makeText(LoginActivity.this, "Brand deleted", Toast.LENGTH_LONG).show();*//*
            }
        });*/
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            if (mAuthTask.loginUser()) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("userEmail", mEmailView.getText().toString());
                startActivity(i);
            } else {
                showProgress(false);
            }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void seedData() {
        User user1 = new User("john.smith@google.com", "John", "Smith", "4031234567", "00000", "2500 University Dr NW", "Calgary", "AB", "T2N1N4", "Canada");
        user1.addUser(db);
        User user2 = new User("jane.doe@google.com", "Jane", "Doe", "4039876543", "00000", "6320 Taralea Park NE", "Calgary", "AB", "T3J5C4", "Canada");
        user2.addUser(db);

        ToolType toolType = new ToolType("Saws", "Tools consisting of a tough blade, wire, or chain with a hard toothed edge. Used for cutting material.");
        toolType.addToolType(db);
        toolType = new ToolType("Drills", "Tools primarily used for making round holes or driving fasteners.");
        toolType.addToolType(db);
        toolType = new ToolType("Compressors", "Mechanical devices that increases the pressure of a gas by reducing its volume.");
        toolType.addToolType(db);
        toolType = new ToolType("Sanders", "Power tools used to smooth surfaces by abrasion with sandpaper.");
        toolType.addToolType(db);

        Brand brand = new Brand("DEWALT");
        brand.addBrand(db);
        brand = new Brand("Bosch");
        brand.addBrand(db);
        brand = new Brand("RYOBI");
        brand.addBrand(db);
        brand = new Brand("RIDGID");
        brand.addBrand(db);
        brand = new Brand("Black & Decker");
        brand.addBrand(db);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ryobi_table_saws_rts11_64_1000);
        Tool tool = new Tool("john.smith@google.com", 1, 3, "10 in. Table Saw with Folding Stand", 2018, "RTS11", image);
        tool.addTool(db);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.dewalt_power_drills_dw130v_64_1000);
        tool = new Tool("jane.doe@google.com", 2, 1, "9 Amp 1/2 in. Spade Handle Drill", 2018, "DW130V", image);
        tool.addTool(db);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.dewalt_power_drills_dw235g_64_1000);
        tool = new Tool("jane.doe@google.com", 2, 1, "7.8 Amp 1/2 in. Variable Speed Reversing Drill", 2017, "DW235G", image);
        tool.addTool(db);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.ryobi_jig_saws_js481lg_64_1000);
        tool = new Tool("john.smith@google.com", 1, 3, "4.8 Amp Variable Speed Orbital Jig Saw", 2018, "JS481LG", image);
        tool.addTool(db);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.dewalt_reciprocating_saws_dwe305_64_1000);
        tool = new Tool("john.smith@google.com", 1, 1, "12-amp Corded Reciprocating Saw", 2018, "DWE305", image);
        tool.addTool(db);

        Ad ad = new Ad("john.smith@google.com", 1, "2018-11-29", "2019-12-29", "Table saw available weekends", "Available only on the weekends. Can keep from Saturday to Sunday", 5);
        ad.addAd(db);
        ad = new Ad("john.smith@google.com", 5, "2018-11-29", "2019-05-31", "Reciprocating saw $5/day", "You can keep it as long as needed", 5);
        ad.addAd(db);
        ad = new Ad("jane.doe@google.com", 2, "2018-11-29", "2019-05-31", "Spade drill", "Only available weekdays", 7);
        ad.addAd(db);

        Availability availability = new Availability(1, 1, 0, 0, 0, 0, 0, 1, "2018-12-01", "2019-12-29");
        availability.addAvailability(db);
        availability = new Availability(2, 1, 1, 1, 1, 1, 1, 1, "2018-11-29", "2019-05-31");
        availability.addAvailability(db);
        availability = new Availability(3, 0, 1, 1, 1, 1, 1, 0, "2018-11-29", "2019-05-31");
        availability.addAvailability(db);

        Request request = new Request("john.smith@google.com", "jane.doe@google.com", 3, "Pickup", 1);
        addRequest(db, request);
        request = new Request("jane.doe@google.com", "john.smith@google.com", 2, "Delivery", 1);
        addRequest(db, request);
        request = new Request("jane.doe@google.com", "john.smith@google.com", 1, "Pickup", 2);
        addRequest(db, request);

        ToolSchedule toolSchedule = new ToolSchedule(2, 1, "2018-12-17", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(2, 1, "2018-12-18", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(2, 1, "2018-12-19", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(5, 2, "2019-01-25", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(5, 2, "2019-01-26", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(5, 2, "2019-01-27", "Pending");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(1, 3, "2019-03-02", "Busy");
        insertToolSchedule(db, toolSchedule);
        toolSchedule = new ToolSchedule(1, 3, "2019-03-03", "Busy");
        insertToolSchedule(db, toolSchedule);

        Toast.makeText(LoginActivity.this, "Database has been seeded", Toast.LENGTH_LONG).show();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        private Boolean loginUser() {
            // Check if user in database
            User user = new User();
            user = user.getUser(db, mEmail);
            if (user == null) {
                Toast.makeText(LoginActivity.this, "No account registered to this email", Toast.LENGTH_LONG).show();
                return false;
            }

            if (!user.getPassword().equals(mPassword)) {
                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }
    }
}

