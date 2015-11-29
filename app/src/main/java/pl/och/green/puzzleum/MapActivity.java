package pl.och.green.puzzleum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import pl.och.green.puzzleum.ui.RoomActivity;

public class MapActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        findViewById(R.id.room_entry).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("Map Activity", "Entering the room");
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
}
