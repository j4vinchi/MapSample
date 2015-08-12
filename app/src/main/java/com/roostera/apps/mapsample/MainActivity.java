package com.roostera.apps.mapsample;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.ui.IconGenerator;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final LatLng Morelia = new LatLng(19.7006, -101.186);
    private GoogleMap map;
    private KmlLayer kmlLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker centro_morelia = map.addMarker(new MarkerOptions().position(Morelia).title("Morelia BUS").snippet("Transporte público"));
        // zoom in the camera to Morelia
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Morelia, 15));
        // animate the zoom process
        map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

        try {
            KmlLayer kmlLayer = new KmlLayer(getMap(), R.raw.gris_1, getApplicationContext());
            kmlLayer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IconGenerator iconFactory = new IconGenerator(this);
        addIcon(iconFactory, "Icon Generator", new LatLng(19.70092, -101.192558));
        iconFactory.setRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        iconFactory.setColor(Color.BLUE);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private void addIcon(IconGenerator iconFactory, String text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        getMap().addMarker(markerOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public GoogleMap getMap() {
        return map;
    }
}
