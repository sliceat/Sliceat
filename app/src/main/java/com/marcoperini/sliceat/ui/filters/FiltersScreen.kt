package com.marcoperini.sliceat.ui.filters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_close_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class FiltersScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listElementFilter: MutableList<CardFilter>
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, FiltersScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters_screen)

        setupView()
        setupToolbar()
        addCardInList()
        clickListener()
    }

    private fun setupView() {
        listElementFilter = mutableListOf()
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun addCardInList() {
        listElementFilter.add(CardFilter(R.drawable.arachidi, R.string.arachidi))
        listElementFilter.add(CardFilter(R.drawable.crostacei, R.string.crostacei))
        listElementFilter.add(CardFilter(R.drawable.frutta_con_guscio, R.string.frutta_con_guscio))
        listElementFilter.add(CardFilter(R.drawable.funghi, R.string.funghi))
        listElementFilter.add(CardFilter(R.drawable.glutine, R.string.glutine))
        listElementFilter.add(CardFilter(R.drawable.lattosio, R.string.lattosio))
        listElementFilter.add(CardFilter(R.drawable.lievito, R.string.lievito))
        listElementFilter.add(CardFilter(R.drawable.lupini, R.string.lupini))
        listElementFilter.add(CardFilter(R.drawable.mais, R.string.mais))
        listElementFilter.add(CardFilter(R.drawable.molluschi, R.string.molluschi))
        listElementFilter.add(CardFilter(R.drawable.pesce, R.string.pesce))
        listElementFilter.add(CardFilter(R.drawable.sedano, R.string.sedano))
        listElementFilter.add(CardFilter(R.drawable.senape, R.string.senape))
        listElementFilter.add(CardFilter(R.drawable.sesamo, R.string.sesamo))
        listElementFilter.add(CardFilter(R.drawable.soia, R.string.soia))
        listElementFilter.add(CardFilter(R.drawable.solfiti, R.string.solfiti))
        listElementFilter.add(CardFilter(R.drawable.uova, R.string.uova))
        listElementFilter.add(CardFilter(R.drawable.zuccheri_aggiunti, R.string.zuccheri_aggiunti))

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FiltersScreen)
            adapter = FiltersAdapter(listElementFilter, resources)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_close_button.visibility = View.VISIBLE
        toolbar.toolbar_close_button.setImageDrawable(resources.getDrawable(R.drawable.icon_cancel, null))
        toolbar.toolbar_title.text = resources.getString(R.string.help)
    }

    private fun clickListener() {
        toolbar.toolbar_close_button.setOnClickListener {
            navigator.goToMapsScreen()
            finish()
            toolbar.toolbar_close_button.visibility = View.GONE
        }

        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToMapsScreen()
            finish()
        }
    }
}
