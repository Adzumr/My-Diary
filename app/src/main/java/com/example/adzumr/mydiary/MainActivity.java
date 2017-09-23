package com.example.adzumr.mydiary;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PointCollecterListerner{

    private PointsCollector pointsCollector = new PointsCollector();
    private Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addTouchListener();
        showPrompt();
        pointsCollector.setListerner(this);
    }

//    This Method Display Dialog To User To Set His Passpoint
    private void showPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setTitle("Create Your PassPoint");
        builder.setMessage("Touch Four Points On The Picture To Set Your PassPoint, You Must Touch That Same Points To Have Access To Your Diary In The Future.");

        AlertDialog dialog = builder.create();
        dialog.show();



    }

    //    This Method Get The Coordinate Of The Points The User Touches
    private void addTouchListener() {
       ImageView image = (ImageView) findViewById(R.id.Pattern_Image_View);
        image.setOnTouchListener(pointsCollector);
    }

    @Override
    public void pointsCollected(List<Point> points) {
        Log.d(DiaryNotes.DebugTag, "Collected points: " + points.size());

        db.getPoints(points);
        List<Point> listPoint = db.retrievePoints();
        for (Point point: listPoint){
            Log.d(DiaryNotes.DebugTag, String.format("Got Points", point.x, point.y));
        }

    }
}
