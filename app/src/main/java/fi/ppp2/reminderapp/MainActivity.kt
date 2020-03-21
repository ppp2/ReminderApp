package fi.ppp2.reminderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java) //voidaan sijoittaa ensin muuttujaan
            startActivity(Intent(applicationContext, TimeActivity::class.java))
        }

        button_map.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        fab_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java) //voidaan sijoittaa ensin muuttujaan
            startActivity(Intent(applicationContext, TimeActivity::class.java))
        }

        fab_map.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
