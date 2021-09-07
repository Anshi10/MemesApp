package com.example.memechat

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memechat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var currentmemeurl:String?=null
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadmeme()
    }
    private fun loadmeme(){
        binding.loading.visibility= View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
// Request a string response from the provided URL.
        val jasonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    currentmemeurl = response.getString("url")
                    Glide.with(this, ).load(currentmemeurl).listener(object:RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            binding.loading.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            binding.loading.visibility=View.GONE
                            return false
                        }
                    }).into (binding.memeimageview)
                },
                Response.ErrorListener{Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
        queue.add(jasonObjectRequest)
    }
    fun shareMeme(view: View) {
       val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme $currentmemeurl")
        val chooser= Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
     loadmeme()
    }
}