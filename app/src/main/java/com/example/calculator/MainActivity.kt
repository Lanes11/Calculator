package com.example.calculator

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.animation.ObjectAnimator
import androidx.core.view.ViewCompat
import android.annotation.SuppressLint
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var result: TextView
    private lateinit var mathOperation: TextView

    private var defaultResultTextSize = 25f
    private var defaultMathOperationTextSize = 45f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        result = binding.result
        mathOperation = binding.mathOperation

        with(binding) {
            setAnimationScaleAndColor(btn0, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn1, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn2, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn3, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn4, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn5, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn6, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn7, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn8, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btn9, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnPercent, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnPoint, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnMinus, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnPlus, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnDivision, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnMultiplication, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnAC, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnBack, scaleB = true, colorB = true)
            setAnimationScaleAndColor(btnEquals, scaleB = true, colorB = false)

            btn0.setOnClickListener {setTextFields("0")}
            btn1.setOnClickListener {setTextFields("1")}
            btn2.setOnClickListener {setTextFields("2")}
            btn3.setOnClickListener {setTextFields("3")}
            btn4.setOnClickListener {setTextFields("4")}
            btn5.setOnClickListener {setTextFields("5")}
            btn6.setOnClickListener {setTextFields("6")}
            btn7.setOnClickListener {setTextFields("7")}
            btn8.setOnClickListener {setTextFields("8")}
            btn9.setOnClickListener {setTextFields("9")}
            btnMinus.setOnClickListener {setTextFields("-")}
            btnPlus.setOnClickListener {setTextFields("+")}
            btnPoint.setOnClickListener {setTextFields(",")}
            btnDivision.setOnClickListener {setTextFields("÷")}
            btnMultiplication.setOnClickListener {setTextFields("*")}


            btnAC.setOnClickListener {
                mathOperation.text = ""
                result.text = "0"
            }

            btnBack.setOnClickListener {
                if(mathOperation.text.isNotEmpty()) {
                    mathOperation.text = mathOperation.text.substring(0, mathOperation.text.length - 1)
                    if (mathOperation.text.isNotEmpty()){
                        executionMathOperation()
                    } else {
                        result.text = "0"
                    }
                }
            }

            btnPercent.setOnClickListener{
                if (mathOperation.text.isNotEmpty() && mathOperation.text.last() !in "-+,÷*()"){
                    mathOperation.text = (mathOperation.text.toString().toDouble() / 100.0).toString()

                    result.text = mathOperation.text
                }
            }

            btnEquals.setOnClickListener {
                result.gravity = Gravity.TOP or Gravity.END
                result.textSize = 45f
                mathOperation.textSize = 25f
            }
        }
    }

    private fun setTextFields(str: String) {
        if (str in "-+,÷*()"){
            if(str == "," && (',' in mathOperation.text.split("*", "-", "+", "÷").last())){
                null
            } else if (mathOperation.text.isNotEmpty()){
                val lastChar = mathOperation.text.last()

                if (mathOperation.text == ""){
                    mathOperation.append("0")
                } else if (lastChar in "-+,÷*()"){
                    mathOperation.text = mathOperation.text.substring(0, mathOperation.length() - 1)
                }

                mathOperation.append(str)
            }
        } else {
            if (str == "0" && mathOperation.text.isEmpty()){
                mathOperation.text = ""
            } else {
                mathOperation.append(str)
                executionMathOperation()
            }
        }
    }

    private fun executionMathOperation() {
        try {
            mathOperation.text = mathOperation.text.toString().replace(',', '.')

            val ex = ExpressionBuilder(mathOperation.text.toString()).build()
            val res = ex.evaluate()

            val longRes = res.toLong()
            if (res == longRes.toDouble()) {
                result.text = getString(R.string.result_format, longRes.toString())
            } else {
                result.text = getString(R.string.result_format, res.toString())
            }

            mathOperation.text = mathOperation.text.toString().replace('.', ',')
            result.text = result.text.toString().replace('.', ',')
        } catch (e: Exception) {
            e.printStackTrace()
            result.text = "Ошибка"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setAnimationScaleAndColor(button: View, scaleB: Boolean, colorB: Boolean) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    if (scaleB){
                        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.9f)
                        val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.9f)
                        scaleX.duration = 100
                        scaleY.duration = 100

                        scaleX.start()
                        scaleY.start()
                    }

                    if (colorB){
                        val colorAnimation = ValueAnimator.ofArgb(
                            Color.parseColor("#323232"),
                            Color.parseColor("#4C4C4C")
                        )
                        colorAnimation.duration = 100

                        colorAnimation.addUpdateListener { animator ->
                            val color = animator.animatedValue as Int
                            ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(color))
                        }

                        colorAnimation.start()
                    }
                }

                android.view.MotionEvent.ACTION_UP -> {
                    if (scaleB){
                        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 0.9f, 1f)
                        val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 0.9f, 1f)
                        scaleX.duration = 100
                        scaleY.duration = 100

                        scaleX.start()
                        scaleY.start()
                    }

                    if (colorB){
                        val colorAnimation = ValueAnimator.ofArgb(
                            Color.parseColor("#4C4C4C"),
                            Color.parseColor("#323232")
                        )
                        colorAnimation.duration = 100

                        colorAnimation.addUpdateListener { animator ->
                            val color = animator.animatedValue as Int
                            ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(color))
                        }


                        colorAnimation.start()
                    }

                    if (result.textSize!=defaultResultTextSize){
                        result.gravity = Gravity.TOP or Gravity.END
                        result.textSize = defaultResultTextSize

                        mathOperation.setPadding(
                            mathOperation.paddingLeft,
                            mathOperation.paddingTop,
                            mathOperation.paddingRight,
                            0
                        )

                        mathOperation.textSize = defaultMathOperationTextSize
                    }

                    v.performClick()
                }

                android.view.MotionEvent.ACTION_CANCEL -> {
                    val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 0.9f, 1f)
                    val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 0.9f, 1f)
                    scaleX.duration = 100
                    scaleY.duration = 100
                    scaleX.start()
                    scaleY.start()
                }
            }
            true
        }
    }
}