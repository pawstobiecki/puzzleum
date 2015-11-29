package pl.och.green.puzzleum.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;

import pl.och.green.puzzleum.R;

public class BoardActivity extends Activity implements GameFinishedListener {
    public static final String LEVEL_TILES = "LEVEL_TILES";
    public static final String LEVEL_IMAGE = "LEVEL_IMAGE";
    public static final String LEVEL_TITLE = "LEVEL_TITLE";
    public static final String LEVEL_DESC = "LEVEL_DESC";
    public static final int RESULT_CODE_IMAGE_NOT_COMPLETE = -1;
    public static final int RESULT_CODE_IMAGE_COMPLETE = 0;
    private GameBoardView gameBoard;
    private Handler handler;
    private ImageView completeImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        gameBoard = (GameBoardView) findViewById(R.id.gameboard);
        completeImage = (ImageView) findViewById(R.id.complete_image);
        completeImage.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(LEVEL_IMAGE, 0)));

        findViewById(R.id.info_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BoardActivity.this)
                        .setMessage(getIntent().getIntExtra(LEVEL_DESC, 0))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        final LinkedList<Integer> tileOrder = (LinkedList<Integer>) getLastNonConfigurationInstance();
        if (tileOrder != null) {
            gameBoard.setTileOrder(tileOrder);
        }

        String title = getIntent().getStringExtra(LEVEL_TITLE);
        getActionBar().setTitle(title);

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameBoard.setGameFinishedListener(BoardActivity.this);
                gameBoard.setVisibility(View.VISIBLE);
                completeImage.setVisibility(View.INVISIBLE);
            }
        }, 1500);
        setResult(RESULT_CODE_IMAGE_NOT_COMPLETE);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                gameBoard.setTileOrder(null);
                gameBoard.fillTiles();
                return true;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onGameFinished() {
        gameBoard.setVisibility(View.INVISIBLE);
        completeImage.setVisibility(View.VISIBLE);
        setResult(RESULT_CODE_IMAGE_COMPLETE);
        findViewById(R.id.info_icon).setVisibility(View.VISIBLE);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        // preserve state when rotated
        return gameBoard.getTileOrder();
    }
}