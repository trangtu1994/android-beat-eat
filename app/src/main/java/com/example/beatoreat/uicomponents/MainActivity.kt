package com.example.beatoreat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.beatoreat.databinding.ActivityMainBinding
import com.example.beatoreat.model.Credential
import com.example.beatoreat.model.CredentialType
import com.example.beatoreat.model.Environment
import com.example.beatoreat.uicomponents.MoviesActivity
import com.example.beatoreat.viewmodelfactory.MainViewModelFactory

open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val environment: Environment = Environment.Staging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        initView()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MainViewModelFactory(this.environment)).get(MainViewModel::class.java)
        viewModel.loginType.observe(this) {
            binding.tvContent.text = viewModel.userTitle.value
        }
    }

    private fun initView() {
        binding.btnMovies.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        doLoginUser1()
        viewModel.loadAllUser()
    }

    fun doLogin(credential: Credential) {
        viewModel.loginUser(credential.username, credential.pwd)
    }

    private fun doLoginUser1() {
        doLogin(Credential("tupizza", "pizza", expectedType = CredentialType.Success))
    }

    private fun doLoginSu() {
        doLogin(Credential("su", "pizza", expectedType = CredentialType.Success))
    }

}