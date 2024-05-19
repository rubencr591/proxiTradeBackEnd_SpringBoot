package com.rubenSL.proxiTrade.model.dtos

import java.sql.Blob

data class ImageDTO (
    var id: Long?,
    var base64: Blob?,
    var productId: Long?)