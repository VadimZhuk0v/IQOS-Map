package com.vadim.iqosmap.utils.marker

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager

class FilteredClusterManager(context: Context, googleMap: GoogleMap): ClusterManager<MarkerIqos>(context, googleMap){

    override fun addItems(items: Collection<MarkerIqos>?) {
        val oldItems = algorithm.items
        val toAddList = ArrayList<MarkerIqos>()
        items!!.forEach {
         if(oldItems.contains(it).not())
             toAddList.add(it)
        }
        oldItems.forEach {
            if(items.contains(it).not()) {
                removeItem(it)
            }
        }

        super.addItems(toAddList)
    }
}