package com.vadmax.iqosmap.data.container

import com.vadmax.iqosmap.data.entities.PointEntity
import com.vadmax.iqosmap.utils.CategoryEnum
import com.vadmax.iqosmap.utils.marker.MarkerIqos

class SortedPoints(list: List<PointEntity>) {

    val listSticks: List<MarkerIqos> = list.filter { it.category == CategoryEnum.STICKS }.map { MarkerIqos(it) }
    val listDevices: List<MarkerIqos> = list.filter { it.category == CategoryEnum.DEVICES }.map { MarkerIqos(it) }
    val listAccessories: List<MarkerIqos> = list.filter { it.category == CategoryEnum.ACCESSORIES }.map { MarkerIqos(it) }
    val listService: List<MarkerIqos> = list.filter { it.category == CategoryEnum.SERVICE }.map { MarkerIqos(it) }
    val listFriendly: List<MarkerIqos> = list.filter { it.category == CategoryEnum.FRIENDLY }.map { MarkerIqos(it) }
    val listBase: List<MarkerIqos> = list.filter { it.category == CategoryEnum.BASE }.map { MarkerIqos(it) }

}