package th.ac.rmutto.shopdee

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val TextViewPrivacyPolicy: TextView = findViewById(R.id.TextViewPrivacyPolicy)
        TextViewPrivacyPolicy.movementMethod = ScrollingMovementMethod()
    }
}