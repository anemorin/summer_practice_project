package com.summer_practice.app_project

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.AppApi.LogInAtr
import com.summer_practice.app_project.databinding.FragmentMyLibraryBinding
import com.summer_practice.app_project.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = ApiClient().client

        val sharedPreferences = activity?.getSharedPreferences("APP",Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("token", null).isNullOrEmpty()) {

                binding.etLogin.visibility = View.VISIBLE
                binding.etPassword.visibility = View.VISIBLE
                binding.bLogIn.visibility = View.VISIBLE

                binding.bLogIn.setOnClickListener{
                    val user = lifecycleScope.async {
                        val user = client.logIn(
                            LogInAtr(
                                binding.etLogin.text.toString(),
                                binding.etPassword.text.toString(),
                            )
                        )
                        user
                    }
                    user.invokeOnCompletion {
                        if (user.getCompleted().result == "ok") {
                            sharedPreferences.edit().putString("token", user.getCompleted().token.session).apply()
                            binding.bLogIn.visibility = View.GONE
                            binding.etLogin.visibility = View.GONE
                            binding.etPassword.visibility = View.GONE
                            binding.tvAlreadyLogged.visibility = View.VISIBLE
                            binding.ibLogout.visibility = View.VISIBLE
                            Snackbar.make(binding.root, R.string.successfully_log_in, Snackbar.LENGTH_LONG).show()
                        }
                        else {
                            Snackbar.make(binding.root, R.string.user_not_found, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
            else {
                binding.tvAlreadyLogged.visibility = View.VISIBLE
                binding.ibLogout.visibility = View.VISIBLE
                binding.ibLogout.setOnClickListener{
                    sharedPreferences.edit().remove("token").commit()
                    Snackbar.make(binding.root, R.string.successfully_log_out, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}