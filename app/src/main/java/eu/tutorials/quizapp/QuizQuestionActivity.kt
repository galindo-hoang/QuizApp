package eu.tutorials.quizapp

import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private val mQuestionList = Constants.getQuestions()
    private var mcurrentQuestion = 1
    private var mCorrect = 0
    private var mChosen:Int? = null
    private var mName: String? = null

    private fun setDefaultOption(){
        val arr = ArrayList<TextView>()
        arr.add(findViewById(R.id.txOptionOne))
        arr.add(findViewById(R.id.txOptionTwo))
        arr.add(findViewById(R.id.txOptionThree))
        arr.add(findViewById(R.id.txOptionFour))

        for(i in arr){
            i.typeface= Typeface.DEFAULT
            i.setTextColor(Color.parseColor("#7A8089"))
            i.background = ContextCompat.getDrawable(this,R.drawable.default_option_border)
        }

    }

    private fun setQuestion(){
        val question = mQuestionList[mcurrentQuestion-1]
        this.setDefaultOption()
        findViewById<ProgressBar>(R.id.progressBar).progress = mcurrentQuestion
        findViewById<TextView>(R.id.tvProgressbar).text = "$mcurrentQuestion/${mQuestionList.size}"
        findViewById<ImageView>(R.id.imageView).setImageResource(question.image)

        findViewById<TextView>(R.id.txOptionOne).text = question.optionOne
        findViewById<TextView>(R.id.txOptionOne).setOnClickListener(this)


        findViewById<TextView>(R.id.txOptionTwo).text = question.optionTwo
        findViewById<TextView>(R.id.txOptionTwo).setOnClickListener(this)


        findViewById<TextView>(R.id.txOptionThree).text = question.optionThree
        findViewById<TextView>(R.id.txOptionThree).setOnClickListener(this)


        findViewById<TextView>(R.id.txOptionFour).text = question.optionFour
        findViewById<TextView>(R.id.txOptionFour).setOnClickListener(this)

        findViewById<Button>(R.id.btnSubmit).text = "Submit"
    }

    private fun selectedQuestion(view: TextView,number: Int){
        this.setDefaultOption()
        view.setTypeface(view.typeface,Typeface.BOLD)
        mName = intent.getStringExtra(Constants.ID_Username)
        view.background = ContextCompat.getDrawable(this,R.drawable.select_option_border)
        mChosen = number
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        findViewById<ProgressBar>(R.id.progressBar).max = mQuestionList.size
        this.setQuestion()
        findViewById<Button>(R.id.btnSubmit).setOnClickListener(this)
    }

    private fun check(p0: Button){
        if(mChosen != mQuestionList[mcurrentQuestion-1].correctAnswer){
            when(mChosen){
                1 -> findViewById<TextView>(R.id.txOptionOne).background = ContextCompat.getDrawable(this,R.drawable.wrong_option_border)
                2 -> findViewById<TextView>(R.id.txOptionTwo).background = ContextCompat.getDrawable(this,R.drawable.wrong_option_border)
                3 -> findViewById<TextView>(R.id.txOptionThree).background = ContextCompat.getDrawable(this,R.drawable.wrong_option_border)
                4 -> findViewById<TextView>(R.id.txOptionFour).background = ContextCompat.getDrawable(this,R.drawable.wrong_option_border)
            }
        }else ++mCorrect
        when(mQuestionList[mcurrentQuestion-1].correctAnswer){
            1 -> findViewById<TextView>(R.id.txOptionOne).background = ContextCompat.getDrawable(this,R.drawable.correct_option_border)
            2 -> findViewById<TextView>(R.id.txOptionTwo).background = ContextCompat.getDrawable(this,R.drawable.correct_option_border)
            3 -> findViewById<TextView>(R.id.txOptionThree).background = ContextCompat.getDrawable(this,R.drawable.correct_option_border)
            4 -> findViewById<TextView>(R.id.txOptionFour).background = ContextCompat.getDrawable(this,R.drawable.correct_option_border)
        }
        if(mcurrentQuestion == mQuestionList.size) p0.text = "Finish"
        else p0.text = "Next question"

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txOptionOne -> this.selectedQuestion(p0 as TextView,1)
            R.id.txOptionTwo -> this.selectedQuestion(p0 as TextView,2)
            R.id.txOptionThree -> this.selectedQuestion(p0 as TextView,3)
            R.id.txOptionFour -> this.selectedQuestion(p0 as TextView,4)
            else -> {
                if((p0 as Button).text == "Submit"){
                    if(mChosen == null) Toast.makeText(this,"Please chose answer",Toast.LENGTH_SHORT).show()
                    else check(p0)
                } else if((p0 as Button).text == "Finish"){
                    val intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra(Constants.ID_Username,mName)
                    intent.putExtra(Constants.Score,mCorrect)
                    intent.putExtra(Constants.total,mQuestionList.size)
                    startActivity(intent)
                    finish()
                } else{
                    mcurrentQuestion += 1
                    setQuestion()
                    mChosen = null
                }
            }
        }
    }
}