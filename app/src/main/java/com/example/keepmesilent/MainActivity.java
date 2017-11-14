package com.example.keepmesilent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.keepmesilent.data.Poi;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    TextView GPSValue;
    double longitudeGPS, latitudeGPS;
    parsePOI poi;
    int ringmode;
    boolean ringmodeFlag;
    AudioManager audiomanage;
    com.example.keepmesilent.data.Location baseLocation;
    SharedPreferences sharedPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSValue = (TextView) findViewById(R.id.GpsValeus);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        StrictMode.setThreadPolicy(policy);
        sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
        toggleGPSUpdates();
        poi = parsePOI.getInstance();
        restoreData();

    }


    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void restoreData(){
            ( (ToggleButton)findViewById(R.id.OnOffButton)).setChecked(sharedPref.getBoolean("OnOffButton", false));
            ( (CheckBox)findViewById(R.id.GalleriesBtn)).setChecked(sharedPref.getBoolean("Galleries", false));
            ( (CheckBox)findViewById(R.id.CinemasBtn)).setChecked(sharedPref.getBoolean("Cinemas", false));
        ( (CheckBox)findViewById(R.id.CalendarBtn)).setChecked(sharedPref.getBoolean("Calendar", false));
        ( (CheckBox)findViewById(R.id.ReligiousBtn)).setChecked(sharedPref.getBoolean("Religious", false));
        ( (CheckBox)findViewById(R.id.LibraryBtn)).setChecked(sharedPref.getBoolean("Library", false));
        ( (CheckBox)findViewById(R.id.MySpotsBtn)).setChecked(sharedPref.getBoolean("MySpots", false));
        ( (CheckBox)findViewById(R.id.NotifyCB)).setChecked(sharedPref.getBoolean("Notify", false));
        ( (CheckBox)findViewById(R.id.AutoReplyCB)).setChecked(sharedPref.getBoolean("AutoReply", false));
        ( (CheckBox)findViewById(R.id.VibrateCB)).setChecked(sharedPref.getBoolean("Vibarate", false));

    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void showNotify(String poiName) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("keepMeSilent")
                .setMessage("We spotted your location as "+ poiName +"\n Do you want to move to silent mode?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {goSilent();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2 * 1 * 1000, 10, locationListenerGPS);

    }

    private final LocationListener locationListenerGPS;
    {
        locationListenerGPS = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitudeGPS = location.getLongitude();
                latitudeGPS = location.getLatitude();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sharedPref.getBoolean("OnOffButton", false)) {
                                if (baseLocation == null ||
                                        poi.getPois().size() == 0 ||
                                        baseLocation.distance(latitudeGPS, longitudeGPS) > 50000) {
                                    baseLocation = new com.example.keepmesilent.data.Location(latitudeGPS, longitudeGPS);
                                    poi.updatePOI(sharedPref, baseLocation);
                                }


                                Poi MyPoi = poi.getPoi(new com.example.keepmesilent.data.Location(latitudeGPS, longitudeGPS));
                                if (MyPoi != null) {
                               if (sharedPref.getBoolean("Notify", false)){
                                   showNotify(MyPoi.getName());
                               }else {
                                   goSilent();
                               }
                                } else {
                                    //    GPSValue.setText("not in POI ");
                                    if (ringmodeFlag) {
                                        audiomanage.setRingerMode(ringmode);
                                        ringmodeFlag = false;
                                    }
                                }
                                //Toast.makeText(MainActivity.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MainActivity.this, "GPS Provider Disabled", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void goSilent() {
        ringmode = audiomanage.getRingerMode();
        ringmodeFlag = true;
        if (sharedPref.getBoolean("Vibrate", false)) {
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else {
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(((RadioButton) view).getText().toString(),  ((RadioButton) view).isChecked());
        editor.commit();
       // Toast.makeText(MainActivity.this, "Please Update POIs", Toast.LENGTH_SHORT).show();
        }

    public void  OnOffButtonClicked(View view) {
        // Is the button now checked?
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("OnOffButton",  ((ToggleButton) view).isChecked());
        editor.commit();
        // Toast.makeText(MainActivity.this, "Please Update POIs", Toast.LENGTH_SHORT).show();
    }

    public void   onCheckBoxClicked (View view) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(((CheckBox) view).getText().toString(), ((CheckBox) view).isChecked());
        editor.commit();
    }

    }




