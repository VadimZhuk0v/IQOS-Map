package com.vadim.iqosmap.data.container

import com.vadim.iqosmap.data.entities.PointEntity
import com.vadim.iqosmap.utils.CategoryEnum
import com.vadim.iqosmap.utils.marker.MarkerIqos

class SortedPoints(list: List<PointEntity>) {

    val listSticks: List<MarkerIqos>
    val listDevices: List<MarkerIqos>
    val listAccessories: List<MarkerIqos>
    val listService: List<MarkerIqos>
    val listFriendly: List<MarkerIqos>
    val listBase: List<MarkerIqos>

    init {
        listSticks = list.filter { it.category == CategoryEnum.STICKS }.map { MarkerIqos(it) }
        listDevices = list.filter { it.category == CategoryEnum.DEVICES }.map { MarkerIqos(it) }
        listAccessories = list.filter { it.category == CategoryEnum.ACCESSORIES }.map { MarkerIqos(it) }
        listService = list.filter { it.category == CategoryEnum.SERVICE }.map { MarkerIqos(it) }
        listFriendly = list.filter { it.category == CategoryEnum.FRIENDLY }.map { MarkerIqos(it) }
        listBase = list.filter { it.category == CategoryEnum.BASE }.map { MarkerIqos(it) }
    }

}