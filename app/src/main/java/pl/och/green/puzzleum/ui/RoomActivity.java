package pl.och.green.puzzleum.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.och.green.puzzleum.R;
import pl.och.green.puzzleum.data.Level;

public class RoomActivity extends Activity {
    static final String TEST_ROOM = "test_room";
    static final String KEY_TEMPLATE = "image_%d_unlocked";
    static final int MAX_LEVEL = 6;

    private List<ImageButton> images;
    private SharedPreferences sharedPrefs;
    private ArrayList<Level> levels;
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        levels = getSampleLevels();
        GridView roomGrid = (GridView) findViewById(R.id.room_grid);
        adapter = new RoomAdapter(this, R.layout.room_grid_item, levels);
        roomGrid.setAdapter(adapter);

        sharedPrefs = getSharedPreferences(TEST_ROOM, Context.MODE_PRIVATE);
        allowAccessToFirstLevel();

        adapter.notifyDataSetChanged();
    }

    private void allowAccessToFirstLevel() {
        String firstLevelKey = getKeyForLevelNumber(1);
        if (!sharedPrefs.contains(firstLevelKey)) {
            sharedPrefs.edit().putBoolean(firstLevelKey, false).apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == BoardActivity.RESULT_CODE_IMAGE_COMPLETE) {
            sharedPrefs.edit().putBoolean(getKeyForLevelNumber(requestCode), true).apply();
            if (requestCode < MAX_LEVEL) {
                sharedPrefs.edit().putBoolean(getKeyForLevelNumber(requestCode + 1), false).apply();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private ArrayList<Level> getSampleLevels() {
        return new ArrayList<>(Arrays.asList(
                new Level(getBitmap(R.drawable.level1_1), 2, R.drawable.level1_1, getString(R.string.level1_1), R.string.level1_1_desc),
                new Level(getBitmap(R.drawable.level1_2), 3, R.drawable.level1_2, getString(R.string.level1_2), R.string.level1_2_desc),
                new Level(getBitmap(R.drawable.level1_3), 3, R.drawable.level1_3, getString(R.string.level1_3), R.string.level1_3_desc),
                new Level(getBitmap(R.drawable.level1_4), 4, R.drawable.level1_4, getString(R.string.level1_4), R.string.level1_4_desc),
                new Level(getBitmap(R.drawable.level1_5), 4, R.drawable.level1_5, getString(R.string.level1_5), R.string.level1_5_desc),
                new Level(getBitmap(R.drawable.level1_6), 5, R.drawable.level1_6, getString(R.string.level1_6), R.string.level1_6_desc)
        ));
    }

    private Bitmap getBitmap(int imageId) {
        return ((BitmapDrawable) ContextCompat.getDrawable(this, imageId))
                    .getBitmap();
    }

    private String getKeyForLevelNumber(int number) {
        return String.format(KEY_TEMPLATE, number);
    }
}
