package orioltorner.ioc.easyrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class VPC extends AppCompatActivity {

    ImageButton bttnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpc);

        bttnMenu = findViewById(R.id.bttnMenu);

        bttnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(VPC.this, orioltorner.ioc.easyrestaurant.Taules.class);
                //startActivity(intent);
            }
        });
    }
}