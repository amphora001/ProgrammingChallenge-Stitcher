/** 
 * Programming challenge from Stitcher 
 * 
 * The goal is to have each button change to a random color every second.  
 * Once you press a button, they should all turn that color.
 * 
 * @author Leo Kwong 
 */ 

package com.stitcher.androidchallenge;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

	private Random rand = new Random(System.currentTimeMillis());
	private Timer timer; //use to repeatedly change color
	
	//task to randomize color in UI thread
	class ColorChangeTask extends TimerTask {
        public void run() {
        	MainActivity.this.runOnUiThread(new Runnable(){
        	    public void run(){
        	    	initRandomButtonColor();
        	    }
        	});
        }
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addObservers(getButtonIds());
        startTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * start the color changing task 
     */
    private void startTimer() {
    	System.out.println("startTimer");
    	if (timer == null) {
    		timer = new Timer();
    	}
        timer.scheduleAtFixedRate(new ColorChangeTask(), 0, 1000);
    }
    
    /**
     * stop and nullify coloring changing timer
     */
    private void stopTimer() {
    	System.out.println("stopTimer");
    	if (timer != null) {
    		timer.cancel();
    	}
    	timer = null;
    }
    
    /**
     * returns the ID of 9 buttons
     */
    private int[] getButtonIds() {
    	int[] btnIds = {
    			R.id.button1,
    			R.id.button2,
    			R.id.button3,
    			R.id.button4,
    			R.id.button5,
    			R.id.button6,
    			R.id.button7,
    			R.id.button8,
    			R.id.button9,
    	};
    	return btnIds;
    }
    
    /**
     * set background of all 9 buttons to random color
     */
    private void initRandomButtonColor() {
    	System.out.println("initRandomButtonColor");
    	int[] btnIds = getButtonIds();
    	for (int i=0; i<btnIds.length; i++) {
    		initRandomButtonColor((Button)findViewById(btnIds[i]));
    	}
    }
    
    /**
     * set background color of given button
     * @param btn
     */
    private void initRandomButtonColor(Button btn){
    	int r = rand.nextInt(256);
    	int b = rand.nextInt(256);
    	int g = rand.nextInt(256);
    	int c = Color.argb(255, r, g, b);
    	btn.setTag(c);
    	btn.setBackgroundColor(c);
    }
    
    /**
     * set background color of all 9 button to given color
     * @param c, sold color as integer
     */
    private void initButtonColor(int c) {
    	System.out.println("initRandomButtonColor c=" + c);
    	int[] btnIds = getButtonIds();
    	for (int i=0; i<btnIds.length; i++) {
    		Button btn = (Button)findViewById(btnIds[i]);
    		btn.setTag(c);
    		btn.setBackgroundColor(c);
    	}
    }
    
    /**
     * add button click listener to buttons with the given IDs
     * Note: ID must be of a button
     * @param btnIds, IDs of buttons
     */
    private void addObservers(int[] btnIds) {
    	OnClickListener btnListener = new OnClickListener() {
    		@Override
    	    public void onClick(final View v) {
    			stopTimer();
    			initButtonColor((Integer) v.getTag());
    		}
    	};
    	for (int i=0; i<btnIds.length; i++) {
    		Button btn = (Button)findViewById(btnIds[i]);
    		btn.setOnClickListener(btnListener);
    	}
    }
}
