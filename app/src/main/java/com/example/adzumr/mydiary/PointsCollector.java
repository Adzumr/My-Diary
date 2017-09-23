package com.example.adzumr.mydiary;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adzumr on 9/20/17.
 */

public class PointsCollector implements View.OnTouchListener {

    private PointCollecterListerner listerner;
    private List<Point> points = new ArrayList<Point>();

    public PointCollecterListerner getListerner() {
        return listerner;
    }

    public void setListerner(PointCollecterListerner listerner) {
        this.listerner = listerner;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int x = Math.round(motionEvent.getX());
        int y = Math.round(motionEvent.getY());

        String message = String.format("Coordinates: (%d, %d)", x, y);
        Log.d(DiaryNotes.DebugTag, message);

        points.add(new Point(x, y));
        if (points.size() == 4){
            if (listerner != null){
                listerner.pointsCollected(points);
                points.clear();
            }
        }

        return false;
    }
}
