package fi.ppp2.reminderapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_view_item.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fabOpened = false

        createList()


        /*button_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java)
            startActivity(Intent(applicationContext, TimeActivity::class.java))
        }*/

        deleteNotifications.setOnClickListener{
            //val intent = Intent(applicationContext, MapActivity::class.java)
            //startActivity(intent)
            //Toast.makeText(applicationContext, "Testing toasts!", Toast.LENGTH_SHORT).show()
            //it.setBackgroundColor(Color.CYAN)
            //textView.text = "Testing text change"


            deleteNotifications()
            refreshList()

        }

        fab_time.setOnClickListener{
            //toast("Mobile Computing")
            //val intent = Intent(applicationContext, TimeActivity::class.java)
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

                /*if (reminders.isNotEmpty()) {*/
                    val adapter = ReminderAdapter(applicationContext, reminders)
                    adapter.notifyDataSetChanged()
                    list.adapter = adapter
                    adapter.notifyDataSetChanged()
                list.adapter = adapter
                /*} else {

                    toast("No reminders yet")
                }*/
                if (reminders.isEmpty()) {
                    toast("No reminders yet")
                }


            }
        }
    }

    private fun createList(){
        doAsync {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "reminders").build()
            val reminders = db.reminderDao().getReminders()

            db.close()

            uiThread {
                val adapter = ReminderAdapter(applicationContext, reminders)
                adapter.notifyDataSetChanged()
                list.adapter = adapter
            }
        }
    }

    private fun deleteNotifications(){
        doAsync {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "reminders").build()
            db.reminderDao().deleteAll()
            db.close()

            uiThread{
                val reminder = Reminder(
                    uid = null,
                    time = null,
                    location = null,
                    message = "No reminders yet!"
                )
                val data :List<Reminder> = listOf(reminder)
                val reminderAdapter = ReminderAdapter(applicationContext, data)
                list.adapter = reminderAdapter
            }
        }


    }

    companion object {
        val CHANNEL_ID = "REMINDER_CHANNEL_ID"
        var notificationID = 1567
        fun showNotification(context: Context, message: String) {
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(context.getString(R.string.app_name)) //Resources, values, strings ->appname
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
