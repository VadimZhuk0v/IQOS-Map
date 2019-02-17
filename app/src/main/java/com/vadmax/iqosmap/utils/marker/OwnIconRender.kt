package com.vadmax.iqosmap.utils.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer


class OwnIconRender(val context: Context, googleMap: GoogleMap, clusterManager: ClusterManager<MarkerIqos>) :
    DefaultClusterRenderer<MarkerIqos>(context, googleMap, clusterManager) {


    override fun onBeforeClusterItemRendered(item: MarkerIqos?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        item?.pointEntity?.category?.iconMap?.let { markerOptions?.icon(BitmapDescriptorFactory.fromResource(it)) }
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MarkerIqos>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)
        val size = cluster!!.size
        val bm = drawableToBitmap(ContextCompat.getDrawable(context, cluster.items.first().pointEntity.category.iconCluster)!!)
        val canvas = Canvas(bm)
        val paint = Paint()

        paint.color = Color.WHITE
        paint.textSize = 40F

        val bounds = Rect()

        paint.getTextBounds(size.toString(), 0, size.toString().length, bounds)

        var x = (bm.width - bounds.width()) / 2f
        var y = (bm.height + bounds.height()) / 2f

        canvas.drawText(size.toString(), x, y, paint)

        canvas.drawBitmap(bm, 0F, 0F, paint)

        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(bm))
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}