package com.faridrjb.whattocook

class Food {

    var foodName: String? = null
    var initsNeeded: String? = null
    var initsAmount: String? = null
    var essInitsNeeded: String? = null
    var instruction: String? = null
    var photo: String? = null

    override fun toString(): String {
        return foodName!!
    }

    companion object {
        const val KEY_NAME = "foodName"
        const val KEY_INITS = "initsNeeded"
        const val KEY_AMOUNT = "initsAmount"
        const val KEY_ESS_INITS = "essInitsNeeded"
        const val KEY_INSTR = "instruction"
        const val KEY_PHOTO = "photo"
    }
}