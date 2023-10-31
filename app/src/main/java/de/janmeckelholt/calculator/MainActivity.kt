package de.janmeckelholt.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.pow

enum class Operation {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
}

var num1: Double? = null
var num2: Double? = null
var result: Double? = null
var operator: Operation? = null
var afterDecimal : Int? = null

class MainActivity : AppCompatActivity() {
    var tvInput: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun clickOnDigit(view: View) {
        tvInput?.let { tvI ->
            afterDecimal = afterDecimal?.plus(1)
            if (operator == null) {
                afterDecimal?.let { aD ->
                    num1 = num1?.plus(((view as Button).text.toString().toDouble())/(10.0.pow(aD)))
                } ?: run {
                    num1 = num1?.times(10)?.plus((view as Button).text.toString().toDouble())
                }
                num1 = num1 ?: (view as Button).text.toString().toDouble()
            } else {
                afterDecimal?.let { aD ->
                    num2 = num2?.plus(((view as Button).text.toString().toDouble())/(10.0.pow(aD)))
                } ?: run {
                    num2 = num2?.times(10)?.plus((view as Button).text.toString().toDouble())
                }
                num2 = num2 ?: (view as Button).text.toString().toDouble()
            }
            if (result != null) {
                result = null
                tvI.text = ""
            }
            if (tvI.text.toString() == "0") {
                tvI.text = ""
            }
            tvI.append((view as Button).text.toString())
        }
    }

    fun clickOnCLR(view: View) {
        tvInput?.let {
            it.text = "0"
        }
        num1 = null
        num2 = null
        result = null
        operator = null
        afterDecimal = null
    }
    fun clickOnEqual(view: View) {
        tvInput?.let {
            if ((num1 != null) && (num2 != null) && (operator != null)) {
                when (operator) {
                    Operation.ADDITION -> result = num1!! + num2!!
                    Operation.SUBTRACTION -> result = num1!! - num2!!
                    Operation.MULTIPLICATION -> result = num1!! * num2!!
                    Operation.DIVISION -> result = num1!! / num2!!
                    else -> {}
                }
                num1 = null
                num2 = null
                operator = null
                afterDecimal = null
                it.text = removeZero(String.format("%.10f", result))
            }
        }
    }
    fun clickOnOperation(view: View) {
        tvInput?.let {
            if (num2 == null && operator == null) {
                if (it.text.toString().endsWith(".")){
                    it.append("0")
                }
                it.append(" ${(view as Button).text} ")
                num1 = num1 ?: result ?: 0.0
                result = null
                afterDecimal = null
                when ((view as Button).text.toString()) {
                    "+" -> operator = Operation.ADDITION
                    "-" -> operator = Operation.SUBTRACTION
                    "*" -> operator = Operation.MULTIPLICATION
                    "/" -> operator = Operation.DIVISION
                }
            }

        }
    }

    fun clickOnDecimal(view: View) {
        tvInput?.let {
            if (result != null) {
                result = null
                it.text = ""
            }
            if (afterDecimal == null) {
                afterDecimal = 0
                if (num1 == null) {
                    num1 = 0.0
                    it.append("0")
                }
                if (operator != null && num2 == null) {
                    num2 = 0.0
                    it.append("0")
                }
                it.append(".")
            }

        }
    }

    private fun removeZero(str : String) : String {
        var l = str.length +1
        var s = str
        while (s.length < l) {
            l = s.length
            s = s.removeSuffix("0")
        }
        return s.removeSuffix(".")
    }

}

