package fi.ppp2.reminderapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.random.Random

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

        /*val data = arrayOf("Oulu", "Helsinki", "Pori")
        val reminderAdapter = ReminderAdapter(applicationContext, data)
        list.adapter = reminderAdapter*/



    }

    override fun onResume() {
        super.onResume()

        refreshList()

        /*val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(object)*/
    }

    private fun refreshList(){
        doAsync {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "reminders").build()
            val reminders = db.reminderDao().getReminders()

            db.close()

            uiThread {

                if (reminders.isNotEmpty()) {
                    val adapter = ReminderAdapter(applicationContext, reminders)
                    list.adapter = adapter
                } else {
                    toast("No reminders yet")
                }


            }
        }
    }

    companion object {
        val CHANNEL_ID = "REMINDER_CHANNEL_ID"
        var notificationID = 1567
        fun showNotification(context: Context, message: String) {
            var notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(context.getString(R.string.app_name)) //Resources, values, strings ->appname
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channel = NotificationChannel(CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = context.getString(R.string.app_name)
                }

                notificationManager.createNotificationChannel(channel)
            }
            val notification = notificationID + Random(notificationID).nextInt(1, 30)
            notificationManager.notify(notification, notificationBuilder.build())
        }
    }
}
