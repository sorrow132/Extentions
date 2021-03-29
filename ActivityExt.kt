fun Activity.hideKeyboard(mView: View? = null) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = mView ?: this.currentFocus
    if (view == null) {
        view = this.findViewById<View>(android.R.id.content)?.rootView
    }
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}
