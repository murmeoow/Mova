package dm.sample.mova.ui.viewmodel.pincode


enum class PinCodeMode(val id: Int) {
    Create(id = 0), Confirm(id = 1), Apply(id = 2);

    companion object {
        fun fromId(id: Int) = PinCodeMode.values().first { it.id == id }
    }
}