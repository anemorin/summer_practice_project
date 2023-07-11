package com.summer_practice.app_project

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.AppApi.LogInAtr
import com.summer_practice.app_project.databinding.FragmentMyLibraryBinding
import com.summer_practice.app_project.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retroFit = Retrofit.Builder()
            .baseUrl("https://api.mangadex.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service = retroFit.create(AppAPI::class.java)

        val sharedPreferences = activity?.getSharedPreferences("APP",Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("token", null).isNullOrEmpty()) {
                binding.button.setOnClickListener{
                    CoroutineScope(Dispatchers.IO).launch {
                        val user = service.logIn(
                            LogInAtr(

                                binding.login.text.toString(),
                                binding.password.text.toString(),

                            )
                        )
                    }
                }
            }
        }
    }
}