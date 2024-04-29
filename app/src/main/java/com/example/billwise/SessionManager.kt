import android.content.Context

//manage the user session
class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()//start editing the shared preferences

    companion object {
        const val KEY_EMAIL = "email"
    }

    //saves them to the shared preferences
    fun saveSession(email: String, password: String) {
        editor.putBoolean("isLoggedIn", true)//sets a boolean flag to indicate that the user is logged in
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    //retrieves the session data from the shared preferences
    fun getSession(): HashMap<String, String> {
        // returns it as a HashMap
        val sessionData = HashMap<String, String>()
        sessionData["email"] = sharedPreferences.getString("email", "").toString()
        sessionData["password"] = sharedPreferences.getString("password", "").toString()
        sessionData["id"] = sharedPreferences.getString("id","").toString()
        return sessionData
    }

    //checks whether the user is logged in
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // clears the shared preferences to log the user out.
    fun logout() {
        editor.clear().apply()
    }
    //retrieves the email from the shared preferences.
    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }
}
