package th.ac.rmutto.shopdee

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextFirstname = findViewById<EditText>(R.id.editTextFirstname)
        val editTextLastname = findViewById<EditText>(R.id.editTextLastname)
        val textViewPrivacyPolicy = findViewById<TextView>(R.id.textViewPrivacyPolicy)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        textViewPrivacyPolicy.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonSubmit.setOnClickListener {

            if(editTextUsername.text.toString() == ""){
                editTextUsername.error = "กรุณาระบุชื่อผู้ใช้"
                return@setOnClickListener
            }

            if(editTextPassword.text.toString() == ""){
                editTextPassword.error = "กรุณาระบุรหัสผ่าน"
                return@setOnClickListener
            }

            if(editTextFirstname.text.toString() == ""){
                editTextFirstname.error = "กรุณาระบุชื่อ"
                return@setOnClickListener
            }

            if(editTextLastname.text.toString() == ""){
                editTextPassword.error = "กรุณาระบุนามสกุล"
                return@setOnClickListener
            }

            val url = getString(R.string.root_url) + getString(R.string.register_url)

            val okHttpClient = OkHttpClient()
            val formBody: RequestBody = FormBody.Builder()
                .add("username",editTextUsername.text.toString())
                .add("password",editTextPassword.text.toString())
                .add("firstName",editTextFirstname.text.toString())
                .add("lastName",editTextLastname.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            val response = okHttpClient.newCall(request).execute()

            if(response.isSuccessful){
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()
                val message = obj["message"].toString()

                if (status == "true") {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

                    //redirect to main page
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(applicationContext, "ไม่สามารถเชื่อต่อกับเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show()
            }
        }

    }
}