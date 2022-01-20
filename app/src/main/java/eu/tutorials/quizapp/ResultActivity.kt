package eu.tutorials.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.*

class ResultActivity : AppCompatActivity() {
    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        hideSystemBars()
        findViewById<TextView>(R.id.tvResultName).text = intent.getStringExtra(Constants.ID_Username)
        findViewById<TextView>(R.id.tvResultScore).text = "Your Score is ${intent.getIntExtra(Constants.Score,0)} out of ${intent.getIntExtra(Constants.total,0)}"

        findViewById<Button>(R.id.btnFinish).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}