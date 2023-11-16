package com.example.gimnasio_grupo3.Firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.gimnasio_grupo3.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import java.io.File

class FirebaseStorageConnection(  ) {

    fun getImage(imageView: ImageView, path: String) {
        val storageRef: StorageReference = Firebase.storage.reference.child(path)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(imageView)
        }.addOnFailureListener {

        }
    }

    fun getImageLoadingAndImgDefault(imageView: ImageView, path: String) {
        imageView.setImageResource(R.drawable.loading)
        val storageRef: StorageReference = Firebase.storage.reference.child(path)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(imageView)
        }.addOnFailureListener {
            imageView.setImageResource(R.drawable.avatar)
        }
    }


    fun deleteImage( path: String) {
        val storageRef: StorageReference = Firebase.storage.reference.child(path)
        storageRef.delete().addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun uploadImage(newImgUri: Uri?, path:String, v: View) {

        if (newImgUri != null) {

            var storageRef: StorageReference = FirebaseStorage.getInstance().reference.child(path)
            var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

            newImgUri?.let {
                try {
                    storageRef.putFile(it).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            storageRef.downloadUrl.addOnSuccessListener { uri ->
                                val map = HashMap<String, Any>()
                                map["pic"] = uri.toString()
                                firebaseFirestore.collection("images").add(map)
                                    .addOnCompleteListener { firestoreTask ->

                                        if (!firestoreTask.isSuccessful)
                                            throw Error("Error uploading image")
                                    }
                            }
                        } else {
                            throw Error("Error uploading image")
                        }
                    }
                }catch (e:Error){
                    Snackbar.make(v, e.toString(), Snackbar.LENGTH_LONG).show()
                    Log.d("uploading image", e.toString())
                }

            }
        }
    }

}