package th.ac.rmutto.shopdee
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class CustomerFragment : Fragment() {
    var imageViewFile: ImageView? = null
    var textViewUsername: TextView? = null
    var textViewFirstName: TextView? = null
    var textViewLastName: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_customer, container, false)
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val sharedPrefer = requireContext().getSharedPreferences(
            "appPrefer", Context.MODE_PRIVATE)
        val custID = sharedPrefer?.getString("custIDPref", null)
        val token = sharedPrefer?.getString("tokenPref", null)

        imageViewFile = root.findViewById<ImageView>(R.id.imageViewFile)
        textViewUsername = root.findViewById<TextView>(R.id.textViewUsername)
        textViewFirstName = root.findViewById<TextView>(R.id.textViewFirstName)
        textViewLastName = root.findViewById<TextView>(R.id.textViewLastName)
        var buttonUpdate = root.findViewById<Button>(R.id.buttonUpdate)
        var buttonLogout = root.findViewById<Button>(R.id.buttonLogout)
        viewUser(custID!!, token!!)
        return root
    }
    fun viewUser(custID: String, token: String)
    {
        //Log.d("tag1", token)
        var url: String = getString(R.string.root_url) + getString(R.string.profile_url) + custID
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")  // Assuming it's a Bearer token
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val data = JSONObject(response.body!!.string())
            if (data.length() > 0) {
                var imageFile = data.getString("imageFile")
                var username = data.getString("username")
                var firstName = data.getString("firstName")
                var lastName = data.getString("lastName")

                if (!imageFile.equals("null") && !imageFile.equals("")){
                    val image_url = getString(R.string.root_url) +
                            getString(R.string.customer_image_url) + imageFile
                    Picasso.get().load(image_url).into(imageViewFile)
                }
                if(username.equals("null"))username = "-"
                textViewUsername?.text = username
                if(firstName.equals("null"))firstName = "-"
                textViewFirstName?.text = firstName
                if(lastName.equals("null"))lastName = "-"
                textViewLastName?.text = lastName
            }
        }
    }
}
