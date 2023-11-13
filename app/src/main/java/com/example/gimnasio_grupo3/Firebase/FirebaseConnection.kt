package com.example.gimnasio_grupo3.Firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
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

//    fun getImageCache(imageView: ImageView, path: String) {
//        val cacheFile = File(imageView.context.cacheDir, "picasso-cache/$path")
//
//            if (cacheFile.exists()) {
//                // Image is cached, load it from cache
//                Picasso.get().load(cacheFile).into(imageView)
//            } else {
//                val storageRef: StorageReference = Firebase.storage.reference.child(path)
//                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                    // Load the Firebase image into the ImageView and cache it
//                    Picasso.get().load(uri).into(imageView)
//                    // Cache the image
//                    Picasso.get().load(uri).into(object : com.squareup.picasso.Target {
//                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                            cacheFile.parentFile?.mkdirs()
//                            cacheFile.createNewFile()
//                            val outputStream = cacheFile.outputStream()
//                            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                            outputStream.flush()
//                            outputStream.close()
//                        }
//
//                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                            // Handle failure to load the bitmap
//                        }
//
//                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                            // Handle loading image
//                        }
//                    })
//                }.addOnFailureListener {
//
//                }
//            }
//
//
//
//    }

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


    fun uploadImageCache(newImgUri: Uri?, path:String, v: View,imageView: ImageView) {

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

                                        if (!firestoreTask.isSuccessful) throw Error("Error uploading image")
                                        else getImage(imageView,path)
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



//    private var FirebaseStorageConnection = FirebaseStorageConnection()

//    private fun registerClickEventForImg() {
//        imgView.setOnClickListener {
//            resultLauncher.launch("image/*")
//        }
//    }
//
//    private val resultLauncher = registerForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) {
//
//        imageUri = it
//        imgView.setImageURI(it)
//    }

    //
//
//
//
// implementation ("com.squareup.picasso:picasso:2.8")

}