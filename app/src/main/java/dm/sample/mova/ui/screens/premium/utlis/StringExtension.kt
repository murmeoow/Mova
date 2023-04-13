package dm.sample.mova.ui.screens.premium.utlis

fun String.isValidWithLuhnAlgorithm() = reversed()
    .map(Character::getNumericValue)
    .mapIndexed { index, digit ->
        when {
            index % 2 == 0 -> digit
            digit < 5 -> digit * 2
            else -> digit * 2 - 9
        }
    }.sum() % 10 == 0

fun isBrandVisa(number: String): Boolean {
    var hasAllowedIINRange = false
    val IINRange = 4
    val IIN = parseIIN(parseNumber(number), 1)

    if (IIN == IINRange) {
        hasAllowedIINRange = true
    }

    return hasAllowedIINRange
}

fun isBrandMaster(number: String): Boolean {
    var hasAllowedIINRange = false
    val IINRange = intArrayOf(51, 55, 222100, 272099)
    val creditCardNumberList = parseNumber(number)
    val firstTwo = parseIIN(creditCardNumberList, 2)
    val firstSix = parseIIN(creditCardNumberList, 6)
    for (i in IINRange[0]..IINRange[1]) {
        if (firstTwo == i) {
            hasAllowedIINRange = true
            break
        }
    }
    if (!hasAllowedIINRange) {
        for (i in IINRange[2]..IINRange[3]) {
            if (firstSix == i) {
                hasAllowedIINRange = true
                break
            }
        }
    }

    return hasAllowedIINRange
}

private fun parseIIN(creditCardNumberList: List<Int>, range: Int): Int {
    val IINString = StringBuilder(range)
    for (i in 0 until range) {
        IINString.append(creditCardNumberList[i])
    }
    return Integer.parseInt(IINString.toString())
}

private fun parseNumber(creditCardNumber: String): List<Int> {
    val creditCardNumberList = mutableListOf<Int>()

    for (number in creditCardNumber.toCharArray()) {
        val tempNumber = Character.getNumericValue(number)
        creditCardNumberList.add(tempNumber)
    }

    return creditCardNumberList
}
