package net.paulbogdan.simplerecipe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: Links,
    val hits: ArrayList<Hit>
) : Parcelable

@Parcelize
data class Hit(
    val recipe: Recipe,
    val _links: Links,
) : Parcelable

@Parcelize
data class Link(
    val href: String,
    val title: String,
) : Parcelable

@Parcelize
data class Links(
    val self: Link,
    val next: Link,
) : Parcelable