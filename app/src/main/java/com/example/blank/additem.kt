package com.example.blank

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blank.databinding.ActivityAdditemBinding
import com.example.blank.model.allmenu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class additem : AppCompatActivity() {

    private lateinit var foodname:String
    private lateinit var foodprice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private  var foodnImageuri: Uri? =null
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private val binding :ActivityAdditemBinding by lazy {
        ActivityAdditemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database=FirebaseDatabase.getInstance()

        binding.additembutton.setOnClickListener {
            foodname = binding.foodnames.text.toString().trim()
            foodprice = binding.FoodPrize.text.toString().trim()
            foodDescription = binding.Description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if (!(foodname.isBlank() || foodDescription.isBlank() || foodprice.isBlank() || foodIngredient.isBlank()) ){
                uploadData()
                Toast.makeText(this, "ItemAdded", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all detail", Toast.LENGTH_SHORT).show()
            }
        }
            binding.selectimage.setOnClickListener{
                picimg.launch("image/*")

        }

        binding.backbutton.setOnClickListener{
            finish()
        }

    }

    private fun uploadData() {
       //get a reference to menunode in database
        val menuRef:DatabaseReference =database.getReference("menu")
        //Generateunique key for the new item
        val newitemKey:String? =menuRef.push().key
        if(foodnImageuri!=null)
        {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_image/${newitemKey}.jpg")
            val uploadTask=imageRef.putFile(foodnImageuri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUri->
                    //create new menu item
                    val newItem= allmenu(
                        foodName =foodname,
                        foodPrice = foodprice,
                        foodDescription =foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUri.toString(),


                    )
                    newitemKey?.let{
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"data uploaded",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"data uploaded fail",Toast.LENGTH_SHORT).show()

                            }
                    }
                }

            } .addOnFailureListener{
                Toast.makeText(this,"data uploaded failed",Toast.LENGTH_SHORT).show()

            }

        }else{
            Toast.makeText(this,"please select an image",Toast.LENGTH_SHORT).show()

        }
    }

   private val picimg =
       registerForActivityResult(ActivityResultContracts.GetContent()) {  uri->

        if(uri !=null)
        {
            binding.selectedimg.setImageURI(uri)
            foodnImageuri=uri
        }
    }
}