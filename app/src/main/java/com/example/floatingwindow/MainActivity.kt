package com.example.floatingwindow

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.floatingwindow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var tinyDB: TinyDB
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
       tinyDB=TinyDB(this)

        binding.button.setOnClickListener {

            if (binding.timeToTriger.text.toString()=="stop"){
                tinyDB.putInt("timeForRandomAzkar",-1)
            }else{
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(this)) {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                        startActivityForResult(intent, 1234)

                    }else{
                        setAlarm(this,binding.timeToTriger.text.toString().toInt())
                        tinyDB.putInt("timeForRandomAzkar",binding.timeToTriger.text.toString().toInt())
                    }
                } else {
                    setAlarm(this,binding.timeToTriger.text.toString().toInt())
                    tinyDB.putInt("timeForRandomAzkar",binding.timeToTriger.text.toString().toInt())

                }

            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1234){
            if (resultCode == Activity.RESULT_OK){

            }
        }
    }
}