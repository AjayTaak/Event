package com.example.event

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TouringGuide : AppCompatActivity() {

    private lateinit var eventRecycleView : RecyclerView

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touring_guide)

        eventRecycleView = findViewById<RecyclerView>(R.id.recyclerview_main)


        fetchData()

    }

    private fun fetchData() {
        val posts = ArrayList<Event>()
        db
            .collection("events")
            .addSnapshotListener { queryDocumentSnapshots, e ->
                for (documentSnapshot in queryDocumentSnapshots!!) {
                    try {
                        val post = Event()
                        val objectMap: Map<String, Any> = documentSnapshot.data as HashMap<String, Any>
                        post.title = objectMap[getString(R.string.title)].toString()
                        post.description = objectMap[getString(R.string.description)].toString()
                        post.startTime = documentSnapshot.getTimestamp("startTime")?.toDate()
                        post.endTime = documentSnapshot.getTimestamp("endTime")?.toDate()

                        posts.add(post)
                    } catch (error: NullPointerException) {

                    }

                }

                eventRecycleView.adapter = RecyclerAdapter(posts)
        }
    }


}