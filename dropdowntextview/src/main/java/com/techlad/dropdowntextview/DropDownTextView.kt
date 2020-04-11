package com.techlad.dropdowntextview

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView


public class DropDownTextView : AppCompatTextView, View.OnClickListener {

    private var options: ArrayList<String> = ArrayList()
    private var dropDownClickListener: DropDownClickListener? = null
    private var xoff = 0
    private var private = 0
    private var yoff = 0
    private var mWidth = -1

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        initView()
    }

    private fun initView() {
        this.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        if (v === this) {
            if (options.size <= 0)
                return

            val window = popupWindowsort(v.getContext())
            window.showAsDropDown(v, xoff, yoff)

            this.background = getResources().getDrawable(
                if (window.isAboveAnchor) R.drawable.round_bottom_only_corner_white_bg else R.drawable.round_top_only_corner_white_bg,
                null
            )

        }
    }

    fun popupWindowsort(context: Context): PopupWindow {

        val popupWindow = PopupWindow(context)

        popupWindow.width = measuredWidth

        popupWindow.setOnDismissListener {
            this.background = getResources().getDrawable(R.drawable.round_corner_white_bg, null)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            context, R.layout.drop_down_item,
            options
        )

        if (options.size > 0 && hint != null && hint.length > 0) {
            if (!isPlaceholderAdded() && text.toString().length > 0) {
                this.options.add(0, hint.toString())
            } else if (isPlaceholderAdded() && text.isBlank()) {
                options.removeAt(0)
            }
        }

        // the drop down list is a list view
        val listViewSort = LayoutInflater.from(context).inflate(R.layout.simple_listview, null, false) as ListView
        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter)

        listViewSort.setAnimation(
            AnimationUtils.loadAnimation(context, R.anim.popup_anim)
        )

        val sage = ColorDrawable(this.resources.getColor(R.color.light_grey, null))

        listViewSort.divider = sage
        listViewSort.dividerHeight = 1

        listViewSort.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                text = ""

                if (!isPlaceholder(position)) {
                    setValue(position)
                    dropDownClickListener?.onDropDownClick(
                        options.get(position),
                        if (isPlaceholderAdded()) position - 1 else position
                    )
                }

//                if (!isPlaceholderAdded()) {
//                    setValue(position)
//                    dropDownClickListener?.onDropDownClick(options.get(finalPosition), finalPosition)
//                }
                popupWindow.dismiss()
            }



//        if (){
//
//        }

        popupWindow.setFocusable(true)

        val listHeight = getHightBasedOnChilds(listViewSort)

        popupWindow.setContentView(listViewSort)
        popupWindow.setElevation(20F)
        popupWindow.setHeight(listHeight)

        val values = IntArray(2)
        this.getLocationInWindow(values)
        var positionOfIcon = values[1]
        println("Position Y:$positionOfIcon")

       // positionOfIcon += this.measuredHeight

        println("Position Y:$positionOfIcon")
        //Get the height of 2/3rd of the height of the screen
        //Get the height of 2/3rd of the height of the screen
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val height: Int = displayMetrics.heightPixels
        val rem = height - positionOfIcon - listHeight

        println("Position H:$height")

        popupWindow.setBackgroundDrawable(
            getResources().getDrawable(if (rem < 0) R.drawable.round_top_only_corner_white_bg else R.drawable.round_bottom_only_corner_white_bg, null)
        )

        return popupWindow
    }


    fun setValue(position: Int) {
        if (options.size > position && position >= 0) {
            try {
                this.setText(options.get(position))
            }
            catch (e: Exception) { }
        }
    }


    fun setOptions(options: ArrayList<String>) {

        this.options = options
    }

    fun setWindowXY(xoff: Int, yoff: Int, width: Int) {
        this.xoff = xoff
        this.yoff = yoff

        mWidth = width
    }

    fun setOptions(options: ArrayList<String>, defaultIndex: Int) {
        setOptions(options)
        if (isPlaceholder(defaultIndex)) {
            setValue(defaultIndex + 1)
        }

    }

    interface DropDownClickListener {
        fun onDropDownClick(value: String?, index: Int)
    }

    fun setClickListener(listener: DropDownClickListener) {
        dropDownClickListener = listener
    }

    fun isPlaceholderAdded(): Boolean {

        return options.size > 0 && options.get(0).equals(hint)
    }

    fun isPlaceholder(index: Int): Boolean {

        return options.size > index && options.get(index).equals(hint)
    }

    open fun getHightBasedOnChilds(listView: ListView): Int {
        val listAdapter = listView.adapter
            ?: // pre-condition
            return 0
        var totalHeight = listView.paddingTop + listView.paddingBottom
        val desiredWidth = MeasureSpec.makeMeasureSpec(
            listView.width,
            MeasureSpec.AT_MOST
        )
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            if (listItem != null) { // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.layoutParams = LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
        }

        return totalHeight + listView.dividerHeight * (listAdapter.count - 1)

    }
}