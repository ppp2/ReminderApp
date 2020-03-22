package fi.ppp2.reminderapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fabOpened = false

        button_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java) //voidaan sijoittaa ensin muuttujaan
            startActivity(Intent(applicationContext, TimeActivity::class.java))
        }

        button_map.setOnClickListener{
            //val intent = Intent(applicationContext, MapActivity::class.java)
            //startActivity(intent)
            Toast.makeText(applicationContext, "Testing toasts!", Toast.LENGTH_SHORT).show()
            it.setBackgroundColor(Color.CYAN)

            //textView.text = "Testing text change"
        }

        fab_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java) //voidaan sijoittaa ensin muuttujaan
            startActivity(Intent(applicationContext, TimeActivity::class.java))
        }

        fab_map.setOnClickListener{
            val intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        }

        fab.setOnClickListener{
            //toast("Not implemented")
            if (!fabOpened) {

                fabOpened = true
                fab_map.animate().translationY(-resources.getDimension(R.dimen.standard_66))
                fab_time.animate().translationY(-resources.getDimension(R.dimen.standard_116))

            } else {

                fabOpened = false
                fab_map.animate().translationY(0f)
                fab_time.animate().translationY(0f)

            }
        }

        val data = arrayOf("Oulu", "Helsinki", "Pori")
        val reminderAdapter = ReminderAdapter(applicationContext, data)
        list.adapter = reminderAdapter

    }
}
