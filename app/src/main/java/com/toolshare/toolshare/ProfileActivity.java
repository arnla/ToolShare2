package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Bundle bundle;
    private DbHandler db;
    private TextView mUsername;
    private LinearLayout mMyTools;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_add:
                    PopupMenu popup = new PopupMenu(ProfileActivity.this, findViewById(R.id.navigation_add));
                    popup.setOnMenuItemClickListener(ProfileActivity.this);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.add_menu, popup.getMenu());
                    popup.show();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bundle = getIntent().getExtras();
        db = new DbHandler(this);

        mUsername = (TextView) findViewById(R.id.tv_username);
        setUsername();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMyTools = (LinearLayout) findViewById(R.id.ll_my_tools);

        loadTools();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tool_option:
                Intent i = new Intent(ProfileActivity.this, NewToolActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                startActivity(i);
                return true;
            case R.id.new_ad_option:
                return true;
            default:
                return false;
        }
    }

    private void setUsername() {
        User user = new User();
        user = user.getUser(db, bundle.getString("userEmail"));
        mUsername.setText(user.getFirstName() + " " + user.getLastName());
    }

    private void loadTools() {
        Tool tool = new Tool();
        List<Tool> tools = tool.getAllToolsByPk(db, bundle.getString("userEmail"));

        for (int i = 0; i < tools.size(); i++) {
            addButton(mMyTools, tools.get(i));
        }
    }

    private void addButton(LinearLayout layout, Tool tool) {
        Button button = new Button(getApplicationContext());
        button.setHeight(15000);
        layout.addView(button);
        button.setText(tool.getName());
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent(ProfileActivity.this, EventActivity.class);
                Bundle b = new Bundle();
                b.putInt("eventId", event.getEventId());
                intent.putExtras(b);
                startActivity(intent);*/
            }
        });
    }
}
