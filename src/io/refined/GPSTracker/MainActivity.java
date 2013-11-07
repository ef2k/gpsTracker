package io.refined.GPSTracker;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	LatLongsOpenHelper latLongsOpenHelper = new LatLongsOpenHelper(this);	
	SQLiteDatabase db;
	
    int clickCount = 0;
    LocationManager locationManager;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);       
        registerListeners();
        initDatabase();
        initLocator();
    }

	private void initLocator() {
		locationManager = (LocationManager) this.getSystemService(
				Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				System.out.println("Location changed to " + location.getLatitude() + ", " + location.getLongitude());
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);		
	}

	private void initDatabase() {
		this.db = latLongsOpenHelper.getWritableDatabase();		
	}

	private void registerListeners() {
		final Button startButton = (Button) findViewById(R.id.startButton);
        final Button stopButton = (Button) findViewById(R.id.stopButton);

        
        startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Clicked on startbutton");
				ContentValues cvs = new ContentValues();
				cvs.put("lat", "-26" + (++clickCount));
				cvs.put("long", "-27" + (++clickCount));
				db.insert("latlongs", null, cvs);
			}
		});
		
        stopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Clicked on stopButton");	
				Cursor cursor = db.rawQuery("SELECT * FROM latlongs", null);
				System.out.println("Database count is " + cursor.getCount());	
				
				while (cursor.moveToNext()) {
					String lat = cursor.getString(0);
					String lon = cursor.getString(1);
					System.out.println("[" + lat + ", " + lon + "]");
				}
			}
		});
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
