package app.elevuslabs.mvvm

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.elevuslabs.mvvm.adapter.MainAdapter
import app.elevuslabs.mvvm.databinding.ActivityMainBinding
import app.elevuslabs.mvvm.repositories.MainRepository
import app.elevuslabs.mvvm.rest.RetrofitService
import app.elevuslabs.mvvm.viewmodel.main.MainViewModel
import app.elevuslabs.mvvm.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var viewModel : MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter {
        Log.i("test", "clicked here!")
        openLink(it.link)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService)))
            .get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter


        val toolbar = binding.liveToolbar
        val mTitle = binding.liveToolbarTitle

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.live_title_toolbar)
        mTitle.text = toolbar.title

        supportActionBar?.let { toolbar ->
            toolbar.setDisplayShowTitleEnabled(false)
        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("test", "onStart")
            adapter.setLiveList(lives)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllLives()

    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}