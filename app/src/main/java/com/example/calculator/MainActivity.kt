package com.example.calculator

import android.os.Bundle
import android.widget.TextView

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var resultText: TextView
    private lateinit var mathOperation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        resultText = binding.resultText
        mathOperation = binding.mathOperation

        with(binding) {
            btn0.setOnClickListener { setTextFields("0") }
            btn1.setOnClickListener { setTextFields("1") }
            btn2.setOnClickListener { setTextFields("2") }
            btn3.setOnClickListener { setTextFields("3") }
            btn4.setOnClickListener { setTextFields("4") }
            btn5.setOnClickListener { setTextFields("5") }
            btn6.setOnClickListener { setTextFields("6") }
            btn7.setOnClickListener { setTextFields("7") }
            btn8.setOnClickListener { setTextFields("8") }
            btn9.setOnClickListener { setTextFields("9") }
            btnMinus.setOnClickListener { setTextFields("-") }
            btnPlus.setOnClickListener { setTextFields("+") }
            btnPoint.setOnClickListener { setTextFields(".") }
            btnDivision.setOnClickListener { setTextFields("/") }
            btnMultiplication.setOnClickListener { setTextFields("*") }
        }


        binding.btnAC.setOnClickListener {
            mathOperation.text = ""
            resultText.text = "0"
        }

        binding.btnBack.setOnClickListener {
            val str = mathOperation.text.toString()
            if(str.isNotEmpty()) {
                mathOperation.text = str.substring(0, str.length - 1)
            }

            executionMathOperation()
        }

        binding.btnEquals.setOnClickListener {
            try {

            } catch (e: Exception) {
                Log.d("Ошибка", "сообщение: ${e.message}")
            }
        }
    }

    private fun setTextFields(str: String) {
        if (str in "-+./*()"){
            val lastChar = mathOperation.text.last()

            if (mathOperation.text == ""){
                mathOperation.append("0")
            } else if (lastChar in "-+./*()"){
                mathOperation.text = mathOperation.text.substring(0, mathOperation.length() - 1)
            }

            mathOperation.append(str)
        } else {
            mathOperation.append(str)
            executionMathOperation()
        }
    }

    private fun executionMathOperation() {
        val lastChar = mathOperation.text.last()

        val ex = if (lastChar in "-+./*()") {
            ExpressionBuilder(mathOperation.text.substring(0, mathOperation.length() - 1)).build()
        } else {
            ExpressionBuilder(mathOperation.text.toString()).build()
        }

        val result = ex.evaluate()

        val longRes = result.toLong()
        if(result == longRes.toDouble()){
            resultText.text = longRes.toString()
        } else {
            resultText.text = result.toString()
        }
    }
}