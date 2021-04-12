package com.chinlung.aclass

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.core.view.get
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val foodlist = mutableListOf<Foods>()
    lateinit var spinnerchoice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var time = ""
//        val datainput = findViewById<EditText>(R.id.DateInput)
        val spinner = findViewById<Spinner>(R.id.ChoiceFood)
        val spendmoney = findViewById<EditText>(R.id.SpendMoney)

        val infoshow = findViewById<TextView>(R.id.InfoShow).also {
            it.movementMethod = ScrollingMovementMethod.getInstance()
        }

        //picker calendar

        val editdate = findViewById<EditText>(R.id.EditPicker)
//        val datePickerlistener =
//            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                editdate.setText("$year/$month/$dayOfMonth")
//            }
        editdate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    editdate.setText("$year/$month/$dayOfMonth")
                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }


        ArrayAdapter.createFromResource(this, R.array.foods, R.layout.spinner_simple)
            .also {
                it.setDropDownViewResource(R.layout.spinner_item)
                spinner.adapter = it
//                spinner.setSelection(0, false)
                spinner.onItemSelectedListener = this
            }


        //早午晚餐
        findViewById<RadioGroup>(R.id.radGroup)
            .setOnCheckedChangeListener { group, checkedId ->
                time = when (checkedId) {
                    R.id.BreakFast -> findViewById<RadioButton>(R.id.BreakFast).text.toString()
                    R.id.Lunch -> findViewById<RadioButton>(R.id.Lunch).text.toString()
                    R.id.Dinner -> findViewById<RadioButton>(R.id.Dinner).text.toString()
                    else -> ""
                }
            }

        //輸入清單
        findViewById<Button>(R.id.btnInput).setOnClickListener {
            savetolist(editdate, time, spinnerchoice, spendmoney)
        }

        //顯示清單
        findViewById<Button>(R.id.btnShow).setOnClickListener {
            var str = ""
            if (foodlist.isEmpty())
                Toast.makeText(this, "清單是空的", Toast.LENGTH_SHORT).show()
            else {
                repeat(foodlist.size) {
                    str =
                        "日期${foodlist[it].date}:${foodlist[it].time} 花$${foodlist[it].money} 吃${foodlist[it].spinner}\n" + str
                }
            }
            infoshow.text = str
        }
    }

//    private fun infoshowtext() {
//        var str = ""
//        if (foodlist.isEmpty())
//            Toast.makeText(this, "清單是空的", Toast.LENGTH_SHORT).show()
//        else {
//            repeat(foodlist.size) {
//                str =
//                    "日期${foodlist[it].date}:${foodlist[it].time} 花$${foodlist[it].money} 吃${foodlist[it].spinner}\n" + str
//            }
//        }
//        infoshow.text = str
//    }

    private fun savetolist(date: EditText, time: String, spinner: String, money: EditText) {

        if (date.text.toString() == "" || time == "" || money.text.toString() == "") {
            val wrong = when {
                date.text.toString() == "" -> "請輸入日期"
                time == "" -> "請輸入時間"
                money.text.toString() == "" -> "請輸入金額"
                else -> "something wrong"
            }
            Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show()
        } else {
            foodlist.add(Foods(date.text.toString(), time, spinner, money.text.toString()))
            Toast.makeText(this, "${foodlist.last()}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerchoice = parent!!.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}