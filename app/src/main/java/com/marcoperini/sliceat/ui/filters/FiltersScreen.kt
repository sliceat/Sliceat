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
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class FiltersScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listElementFilter: MutableList<CardFilter>
    private lateinit var recyclerView: RecyclerView

    private val navigator: Navigator by inject()
    private val prefs: KeyValueStorage by inject()

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, FiltersScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }

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
        listElementFilter.add(CardFilter(R.drawable.arachidi, R.string.arachidi, prefs.getBoolean(resources.getString(R.string.arachidi), false)))
        listElementFilter.add(CardFilter(R.drawable.crostacei, R.string.crostacei, prefs.getBoolean(resources.getString(R.string.crostacei), false)))
        listElementFilter.add(
            CardFilter(R.drawable.frutta_con_guscio, R.string.frutta_con_guscio, prefs.getBoolean(resources.getString(R.string.frutta_con_guscio), false))
        )
        listElementFilter.add(CardFilter(R.drawable.funghi, R.string.funghi, prefs.getBoolean(resources.getString(R.string.funghi), false)))
        listElementFilter.add(CardFilter(R.drawable.glutine, R.string.glutine, prefs.getBoolean(resources.getString(R.string.glutine), false)))
        listElementFilter.add(CardFilter(R.drawable.lattosio, R.string.lattosio, prefs.getBoolean(resources.getString(R.string.lattosio), false)))
        listElementFilter.add(CardFilter(R.drawable.lievito, R.string.lievito, prefs.getBoolean(resources.getString(R.string.lievito), false)))
        listElementFilter.add(CardFilter(R.drawable.lupini, R.string.lupini, prefs.getBoolean(resources.getString(R.string.lupini), false)))
        listElementFilter.add(CardFilter(R.drawable.mais, R.string.mais, prefs.getBoolean(resources.getString(R.string.mais), false)))
        listElementFilter.add(CardFilter(R.drawable.molluschi, R.string.molluschi, prefs.getBoolean(resources.getString(R.string.molluschi), false)))
        listElementFilter.add(CardFilter(R.drawable.pesce, R.string.pesce, prefs.getBoolean(resources.getString(R.string.pesce), false)))
        listElementFilter.add(CardFilter(R.drawable.sedano, R.string.sedano, prefs.getBoolean(resources.getString(R.string.sedano), false)))
        listElementFilter.add(CardFilter(R.drawable.senape, R.string.senape, prefs.getBoolean(resources.getString(R.string.senape), false)))
        listElementFilter.add(CardFilter(R.drawable.sesamo, R.string.sesamo, prefs.getBoolean(resources.getString(R.string.sesamo), false)))
        listElementFilter.add(CardFilter(R.drawable.soia, R.string.soia, prefs.getBoolean(resources.getString(R.string.soia), false)))
        listElementFilter.add(CardFilter(R.drawable.solfiti, R.string.solfiti, prefs.getBoolean(resources.getString(R.string.solfiti), false)))
        listElementFilter.add(CardFilter(R.drawable.uova, R.string.uova, prefs.getBoolean(resources.getString(R.string.uova), false)))
        listElementFilter.add(
            CardFilter(R.drawable.zuccheri_aggiunti, R.string.zuccheri_aggiunti, prefs.getBoolean(resources.getString(R.string.zuccheri_aggiunti), false))
        )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FiltersScreen)
            adapter = FiltersAdapter(listElementFilter, resources, prefs)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_button.visibility = View.VISIBLE
        toolbar.toolbar_button.setImageDrawable(resources.getDrawable(R.drawable.icon_cancel, null))
        toolbar.toolbar_title.text = resources.getString(R.string.help)
    }

    private fun clickListener() {
        toolbar.toolbar_button.setOnClickListener {
            navigator.goToMapsScreen(this)
            finish()
            toolbar.toolbar_button.visibility = View.GONE
        }

        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToMapsScreen(this)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToMapsScreen(this)
    }
}
