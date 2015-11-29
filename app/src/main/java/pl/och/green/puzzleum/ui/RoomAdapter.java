package pl.och.green.puzzleum.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import pl.och.green.puzzleum.R;
import pl.och.green.puzzleum.data.Level;

public class RoomAdapter extends ArrayAdapter {
    private final SharedPreferences sharedPrefs;
    private Activity context;
    private int layoutResourceId;
    private List<Level> data = new ArrayList<>();

    public RoomAdapter(Activity context, int layoutResourceId, ArrayList<Level> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        sharedPrefs = context.getSharedPreferences(RoomActivity.TEST_ROOM, Context.MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageButton) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Level level = data.get(position);
        ImageButton imageButton = holder.image;

        final int levelNumber = position + 1;
        configureLevelThumbnail(levelNumber, level, imageButton);

        return row;
    }

    private void configureLevelThumbnail(final int levelNumber, final Level level, ImageButton imageButton) {
        BitmapDrawable image = new BitmapDrawable(context.getResources(), level.getImage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageButton.setBackground(image);
        } else {
            imageButton.setBackgroundDrawable(image);
        }
        imageButton.setImageDrawable(null);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardActivity.class);
                intent.putExtra(BoardActivity.LEVEL_TILES, level.getTiles());
                intent.putExtra(BoardActivity.LEVEL_IMAGE, level.getImageId());
                intent.putExtra(BoardActivity.LEVEL_TITLE, level.getTitle());
                intent.putExtra(BoardActivity.LEVEL_DESC, level.getDescriptionId());
                Log.i("RoomActivity", "Starting board activity");
                context.startActivityForResult(intent, levelNumber);
            }
        });
    }

    private String getKeyForLevelNumber(int number) {
        return String.format(RoomActivity.KEY_TEMPLATE, number);
    }

    static class ViewHolder {
        ImageButton image;
    }
}
