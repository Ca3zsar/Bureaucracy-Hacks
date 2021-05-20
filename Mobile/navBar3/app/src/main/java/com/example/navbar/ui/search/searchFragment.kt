package com.example.navbar.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navbar.R
import com.example.navbar.ui.institutions.ANAF.ANAF
import com.example.navbar.ui.institutions.CJASI.CJASI
import com.example.navbar.ui.institutions.CJPI.CJPI
import com.example.navbar.ui.institutions.DASI.DASI
import com.example.navbar.ui.institutions.DGASPCI.DGASPCI
import com.example.navbar.ui.institutions.DGpP.DGpP
import com.example.navbar.ui.institutions.DLEPI.DLEPI
import com.example.navbar.ui.institutions.IPJI.IPJI
import com.example.navbar.ui.institutions.PI.PI
import com.example.navbar.ui.institutions.SALUBRIS.SALUBRIS
import kotlinx.android.synthetic.main.fragment_search.*


class searchFragment : Fragment(R.layout.fragment_search), ItemsAdapter.ClickListener {
    private lateinit var srcViewModel: searchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        srcViewModel = ViewModelProvider(this).get(searchViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_search, container, false)
        var textView: TextView = root.findViewById(R.id.text_search)
        srcViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    val imagesName = arrayOf(
        ItemsModel("Agenția Națională de Administrare Fiscală"),
        ItemsModel("Casa Județeană de Asigurări de Sănătate Iași"),
        ItemsModel("Direcția de Asistență Socială Iași"),
        ItemsModel("Direcția Generală de Asistență Socială și Protecția Copilului Iași"),
        ItemsModel("Direcția Locală pentru Evidența Persoanelor Iași"),
        ItemsModel("Inspectoratul de Poliție Județean Iași"),
        ItemsModel("Direcția Generală pentru Pașapoarte"),
        ItemsModel("Casa Județeană de Pensii Iași"),
        ItemsModel("Primăria Iași"),
        ItemsModel("SalubrIS")
    )

    val itemsModelList = ArrayList<ItemsModel>()

    var itemsAdapter : ItemsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for(items in imagesName) {
            itemsModelList.add(items)
        }

        itemsAdapter = ItemsAdapter(this)
        itemsAdapter!!.setData(itemsModelList)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = itemsAdapter

    }

    override fun ClickedItem(itemsModel: ItemsModel) {
        Log.e("TAG", itemsModel.name)

        when(itemsModel.name) {
            "Agenția Națională de Administrare Fiscală"
                -> startActivity(Intent(this.context, ANAF::class.java))
            "Casa Județeană de Asigurări de Sănătate Iași"
                -> startActivity(Intent(this.context, CJASI::class.java))
            "Direcția de Asistență Socială Iași"
                -> startActivity(Intent(this.context, DASI::class.java))
            "Direcția Generală de Asistență Socială și Protecția Copilului Iași"
                -> startActivity(Intent(this.context, DGASPCI::class.java))
            "Direcția Locală pentru Evidența Persoanelor Iași"
                -> startActivity(Intent(this.context, DLEPI::class.java))
            "Inspectoratul de Poliție Județean Iași"
                -> startActivity(Intent(this.context, IPJI::class.java))
            "Direcția Generală pentru Pașapoarte"
                -> startActivity(Intent(this.context, DGpP::class.java))
            "Casa Județeană de Pensii Iași"
                -> startActivity(Intent(this.context, CJPI::class.java))
            "Primăria Iași"
                -> startActivity(Intent(this.context, PI::class.java))
            "SalubrIS"
                -> startActivity(Intent(this.context, SALUBRIS::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val menuItem = menu.findItem(R.id.searchView)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menuItem.actionView = searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(filterString: String?): Boolean {
                itemsAdapter!!.filter.filter(filterString)
                return true
            }

            override fun onQueryTextSubmit(filterString: String?): Boolean {
                itemsAdapter!!.filter.filter(filterString)
                return true
            }
        })

    }
}