package com.example.event

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.event.databinding.ActivityLoginBinding
import com.example.event.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.io.IOException
import java.lang.reflect.Array.get
import java.util.*
import kotlin.collections.HashMap


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            signUpUser()
        }

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnChooseImage.setOnClickListener { launchGallery() }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()


        val userRec = hashMapOf(
                "name" to binding.tvDisplayname.text.toString(),
                "email" to binding.tvUsername.text.toString(),
                "avatar" to uri
        )

        db.collection("users").document("user1")
                .set(userRec)
                .addOnSuccessListener { Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()}
                .addOnFailureListener { Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()}

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                Picasso.get()
                        .load(filePath)
                        .into(binding.ivProfileImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUpUser() {
        if (binding.tvUsername.text.toString().isEmpty()) {
            binding.tvUsername.error = "Please enter email"
            binding.tvUsername.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.tvUsername.text.toString()).matches()) {
            binding.tvUsername.error = "Please enter valid email"
            binding.tvUsername.requestFocus()
            return
        }

        if (binding.tvPassword.text.toString().isEmpty()) {
            binding.tvPassword.error = "Please enter password"
            binding.tvPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(binding.tvUsername.text.toString(), binding.tvPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    uploadImage()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                            }
                } else {
                    Toast.makeText(
                            baseContext, "Sign Up failed. Try again after some time.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}