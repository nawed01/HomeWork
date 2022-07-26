package com.nawed.homework.model

data class Result(
    var imageLqThumbName: String?,
    var imageLqThumbUrl: String?,
    var imageName: String?,
    var imageSizes: List<ImageSize?>?,
    var imageUrl: String?,
    var isMultisizeImg: Int?,
    var isWebP: Int?
)