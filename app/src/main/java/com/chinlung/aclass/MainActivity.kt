package com.chinlung.aclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.core.view.get

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val foodlist = mutableListOf<Foods>()
    lateinit var time: String
    lateinit var spinnerchoice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val datainput = findViewById<EditText>(R.id.DateInput)
        val radgroup = findViewById<RadioGroup>(R.id.radGroup)
        val radbreakfast = findViewById<RadioButton>(R.id.BreakFast)
        val radlunch = findViewById<RadioButton>(R.id.Lunch)
        val raddinner = findViewById<RadioButton>(R.id.Dinner)
        val spinner = findViewById<Spinner>(R.id.ChoiceFood)
        val spendmoney = findViewById<EditText>(R.id.SpendMoney)
        val infoshow = findViewById<TextView>(R.id.InfoShow)
        val btninput = findViewById<Button>(R.id.btnInput)
        val btnshow = findViewById<Button>(R.id.btnShow)

        infoshow.movementMethod = ScrollingMovementMethod.getInstance()

        radgroup.setOnCheckedChangeListener { group, checkedId -> checkradgroup(group, checkedId) }

        ArrayAdapter.createFromResource(
            this,
            R.array.foods,
            android.R.layout.simple_list_item_1
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = it
//            spinner.setSelection(0,false)
        }
        spinner.onItemSelectedListener = this


        btninput.setOnClickListener {
            savetolist(
                datainput,
                time,
                spinner,
                spendmoney.text.toString().toInt()
            )
        }
        btnshow.setOnClickListener { infoshowtext() }
    }

    private fun infoshowtext() {

    }

    private fun checkradgroup(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.BreakFast -> time = findViewById<RadioButton>(R.id.BreakFast).text.toString()
            R.id.Lunch -> time = findViewById<RadioButton>(R.id.Lunch).text.toString()
            R.id.Dinner -> time = findViewById<RadioButton>(R.id.Dinner).text.toString()
        }
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
    }

    private fun savetolist(date: EditText, time: String, spinner: Spinner, money: Int = 0) {
        foodlist.add(Foods(date.text.toString(), time, spinnerchoice, money))
        Toast.makeText(this, "${foodlist[0]}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerchoice = parent!!.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}