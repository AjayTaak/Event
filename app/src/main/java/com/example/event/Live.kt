package com.example.event

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat.getDrawable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import io.matchmore.sdk.Matchmore


class Live : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var mClusterManager: ClusterManager<ClusterMarker>? = null
    private var mClusterManagerRenderer: MyClusterManagerRenderer? = null
    private val mClusterMarkers: ArrayList<ClusterMarker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val partyName: String = intent.getStringExtra("groupName")
        Toast.makeText(this, partyName, Toast.LENGTH_SHORT).show()

        // Add a marker in Birmingham and move the camera
        val birmingham = LatLng(52.47997760879829, -1.904732021917234)
        val outdoorThrift = LatLng(52.47902268561746, -1.9089643773851661)
        val interationalNewComers = LatLng(52.47988023962912, -1.902821953852787)
        val theatreICC = LatLng(52.479025952827044, -1.9095222768550184)
        val fireworkDisplay = LatLng(52.48023411928572, -1.9094601994529918)

        mMap.addMarker(MarkerOptions().position(birmingham).title("You"))

        mMap.addMarker(MarkerOptions().position(outdoorThrift).title("Outdoor Thrift Market").icon(bitmapDescriptionFromVectorShopping(getApplicationContext(), R.drawable.ic_baseline_shopping_cart_24)))
        mMap.addMarker(MarkerOptions().position(interationalNewComers).title("International Music Artists").icon(bitmapDescriptionFromVectorMusic(getApplicationContext(), R.drawable.ic_baseline_music_video_24)))
        mMap.addMarker(MarkerOptions().position(theatreICC).title("ICC Annual Performance").icon(bitmapDescriptionFromVector(getApplicationContext(), R.drawable.ic_baseline_local_play_24)))
        mMap.addMarker(MarkerOptions().position(fireworkDisplay).title("Midnight Firework Display").icon(bitmapDescriptionFromVectorFireworks(getApplicationContext(), R.drawable.ic_baseline_auto_awesome_24)))

        val zoomLevel = 16.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(birmingham, zoomLevel))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bitmapDescriptionFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_baseline_local_play_24, theme)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bitmapDescriptionFromVectorMusic(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_baseline_music_video_24, theme)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bitmapDescriptionFromVectorShopping(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_baseline_shopping_cart_24, theme)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bitmapDescriptionFromVectorFood(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_baseline_food_bank_24, theme)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bitmapDescriptionFromVectorFireworks(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_baseline_auto_awesome_24, theme)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    fun confMatchmore() {
        val API_KEY =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJhbHBzIiwic3ViIjoiZGJhYWVmYjktZmFhNi00ODNmLTg2NjgtMTFkMTUyYjA2ZDBiIiwiYXVkIjpbIlB1YmxpYyJdLCJuYmYiOjE2MTg1MTg1NTcsImlhdCI6MTYxODUxODU1NywianRpIjoiMSJ9.GUvg0iKq-4okawGtdbrLvZ6SWcsNBvka4qEET7KaPg0Io3JZKkwYNCJn8K5b81nTfLai2InxsyOMNzgBACKppQ"
        if (!Matchmore.isConfigured()) {
            Matchmore.config(this, API_KEY, true)
        }
    }
}