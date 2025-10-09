package com.example.iel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class QuizActivity : AppCompatActivity() {

    private lateinit var quizText: TextView
    private lateinit var playButton: Button
    private lateinit var quizCounterText: TextView
    private lateinit var choiceButtons: List<Button>
    private lateinit var tts: TextToSpeech

    private lateinit var quizLayout: LinearLayout
    private lateinit var resultLayout: LinearLayout
    private lateinit var resultText: TextView
    private lateinit var reviewButton: Button
    private lateinit var retryButton: Button

    private var wordsForQuiz: MutableList<Word> = mutableListOf()
    private var currentWord: Word? = null

    private var quizCount = 0
    private val maxQuizCount = 15
    private var correctCount = 0
    private val wrongQuestions = mutableListOf<Word>()

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz)

        quizText = findViewById(R.id.quizText)
        playButton = findViewById(R.id.playButton)
        quizCounterText = findViewById(R.id.quizCounterText)
        choiceButtons = listOf(
            findViewById(R.id.choiceButton1),
            findViewById(R.id.choiceButton2),
            findViewById(R.id.choiceButton3),
            findViewById(R.id.choiceButton4)
        )

        quizLayout = findViewById(R.id.quizLayout)
        resultLayout = findViewById(R.id.resultLayout)
        resultText = findViewById(R.id.resultText)
        reviewButton = findViewById(R.id.reviewButton)
        retryButton = findViewById(R.id.retryButton)

        // ⭐ Word 리스트 직접 정의
        wordsForQuiz = mutableListOf(
            Word("Executive", "명사: 경영진"),
            Word("Inventory", "명사: 재고"),
            Word("Feature", "명사: 특징"),
            Word("Acknowledge", "동사: 수신을 인정하다"),
            Word("Voucher", "명사: 상품권"),
            Word("Estimate", "명사: 견적서"),
            Word("Resume", "동사: 재개하다"),
            Word("Issue", "명사: 문제"),
            Word("Property", "명사: 재산"),
            Word("Eligible", "형용사: 자격이 있는"),
            Word("Initiative", "명사: 새로운 계획"),
            Word("Culinary", "형용사: 요리의"),
            Word("Extensive", "형용사: 광범위한"),
            Word("Deposit", "명사: 예치금"),
            Word("Retail", "명사: 소매"),
            Word("Affordable", "형용사: 가격이 알맞은"),
            Word("Grant", "동사: 승인하다"),
            Word("Significantly", "부사: 상당히"),
            Word("Reserve", "동사: 예약하다"),
            Word("Application", "명사: 신청"),
            Word("Address", "명사: 주소"),
            Word("Suppose", "동사: 추정하다"),
            Word("Charge", "명사: 요금"),
            Word("Expire", "동사: 기한이 만료되다"),
            Word("Refund", "명사: 환불"),
            // === PART 2 ===
            Word("Promote", "동사: 촉진하다"),
            Word("Revenue", "명사: 수익"),
            Word("Opportunity", "명사: 기회"),
            Word("Valid", "형용사: 유효한"),
            Word("Confidential", "형용사: 기밀의"),
            Word("Figure", "명사: 숫자"),
            Word("Traffic", "명사: 교통"),
            Word("Audience", "명사: 청중"),
            Word("Occupy", "동사: 공간을 차지하다"),
            Word("Approximately", "부사: 대략"),
            Word("Complimentary", "형용사: 무료의"),
            Word("Outstanding", "형용사: 미해결의"),
            Word("Imperative", "형용사: 긴급한"),
            Word("Substantial", "형용사: 상당한"),
            Word("Decline", "동사: 감소하다"),
            Word("Balance", "명사: 균형"),
            Word("Account", "명사: 계좌"),
            Word("Proceed", "동사: 계속하다"),
            Word("Amenities", "명사: 편의시설"),
            Word("Renowned", "형용사: 유명한"),
            Word("Preserve", "동사: 보존하다"),
            Word("Simply", "부사: 단순하게"),
            Word("Presence", "명사: 존재"),
            Word("Laboratory", "명사: 실험실"),
            Word("Effective", "형용사: 효과적인"),
            // === PART 3 ===
            Word("Enhance", "동사: 향상시키다"),
            Word("Insurance", "명사: 보험"),
            Word("Spacious", "형용사: 넓은"),
            Word("Ingredient", "명사: 재료"),
            Word("Withdraw", "동사: 철회하다"),
            Word("Banquet", "명사: 연회"),
            Word("Experiment", "명사: 실험"),
            Word("Specific", "형용사: 구체적인"),
            Word("Specialize", "동사: 전문화하다"),
            Word("Affect", "동사: 영향을 미치다"),
            Word("Equip", "동사: 장비를 제공하다"),
            Word("Reimbursement", "명사: 환급"),
            Word("Premises", "명사: 건물과 토지"),
            Word("Renovate", "동사: 수리하다"),
            Word("Versatile", "형용사: 다재다능한"),
            Word("Implement", "동사: 실행하다"),
            Word("Informed", "형용사: 정보에 기반한"),
            Word("Commitment", "명사: 약속"),
            Word("Direct", "형용사: 직접적인"),
            Word("Facilitate", "동사: 용이하게 하다"),
            // === PART 4 ===
            Word("Commission", "명사: 수수료"),
            Word("Cover", "동사: 덮다"),
            Word("Encourage", "동사: 격려하다"),
            Word("Persuade", "동사: 설득하다"),
            Word("Accompany", "동사: 동반하다"),
            Word("Candidate", "명사: 후보자"),
            Word("Renew", "동사: 갱신하다"),
            Word("Launch", "동사: 출시하다"),
            Word("Reliable", "형용사: 믿을 수 있는"),
            Word("Inform", "동사: 알리다"),
            Word("Contribute", "동사: 기여하다"),
            Word("Strategy", "명사: 전략"),
            Word("Motivate", "동사: 동기부여하다"),
            Word("Consider", "동사: 고려하다"),
            Word("Remind", "동사: 상기시키다"),
            Word("Suitable", "형용사: 적합한"),
            Word("Beverage", "명사: 음료"),
            Word("Portable", "형용사: 휴대할 수 있는"),
            Word("Essential", "형용사: 필수적인"),
            Word("Consent", "명사: 동의"),
            Word("Tentative", "형용사: 잠정적인"),
            Word("Respective", "형용사: 각각의"),
            Word("Arrange", "동사: 정리하다"),
            Word("Associate", "동사: 관련시키다"),
            Word("Publicity", "명사: 언론 보도"),
            Word("Instructor", "명사: 강사"),
            Word("Contractor", "명사: 계약자"),
            Word("Relevant", "형용사: 관련 있는"),
            Word("Weekly", "형용사: 매주 열리는"),
            Word("Imply", "동사: 암시하다")
        )

        // TTS 초기화
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        }

        playButton.setOnClickListener {
            currentWord?.let { word ->
                if (!tts.isSpeaking) {
                    tts.speak(word.word, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }

        quizCount = 0
        correctCount = 0
        showNextQuiz()
    }

    private fun showNextQuiz() {
        quizCount++

        quizCounterText.text = "$quizCount / $maxQuizCount"

        choiceButtons.forEach {
            it.setBackgroundColor(getColor(android.R.color.darker_gray))
            it.isEnabled = true
        }

        if (quizCount > maxQuizCount || wordsForQuiz.size < 4) {
            showResult()
            return
        }

        val quizType = (1..3).random()
        when (quizType) {
            1 -> showQuizType1()
            2 -> showQuizType2()
            3 -> showQuizType3()
        }
    }

    private fun showQuizType1() {
        currentWord = wordsForQuiz.random()
        val wrongAnswers = wordsForQuiz.filter { it.word != currentWord?.word }.shuffled().take(3)
        val options = (wrongAnswers + currentWord!!).shuffled()

        quizText.visibility = View.GONE
        playButton.visibility = View.VISIBLE

        for (i in choiceButtons.indices) {
            val button = choiceButtons[i]
            val option = options[i]
            button.text = option.meaning

            button.setOnClickListener {
                handleAnswer(option.word == currentWord?.word, button, currentWord?.meaning)
            }
        }
    }

    private fun showQuizType2() {
        currentWord = wordsForQuiz.random()
        val wrongAnswers = wordsForQuiz.filter { it.word != currentWord?.word }.shuffled().take(3)
        val options = (wrongAnswers + currentWord!!).shuffled()

        quizText.visibility = View.VISIBLE
        playButton.visibility = View.GONE
        quizText.text = currentWord?.meaning

        for (i in choiceButtons.indices) {
            val button = choiceButtons[i]
            val option = options[i]
            button.text = option.word

            button.setOnClickListener {
                handleAnswer(option.word == currentWord?.word, button, currentWord?.word)
            }
        }
    }

    private fun showQuizType3() {
        currentWord = wordsForQuiz.random()
        val wrongAnswers = wordsForQuiz.filter { it.meaning != currentWord?.meaning }.shuffled().take(3)
        val options = (wrongAnswers + currentWord!!).shuffled()

        quizText.visibility = View.VISIBLE
        playButton.visibility = View.GONE
        quizText.text = currentWord?.word

        for (i in choiceButtons.indices) {
            val button = choiceButtons[i]
            val option = options[i]
            button.text = option.meaning

            button.setOnClickListener {
                handleAnswer(option.meaning == currentWord?.meaning, button, currentWord?.meaning)
            }
        }
    }

    private fun handleAnswer(isCorrect: Boolean, selectedButton: Button, correctText: String?) {
        choiceButtons.forEach { it.isEnabled = false }

        if (isCorrect) {
            correctCount++
            selectedButton.setBackgroundColor(getColor(android.R.color.holo_green_light))
        } else {
            wrongQuestions.add(currentWord!!)
            selectedButton.setBackgroundColor(getColor(android.R.color.holo_red_light))
            choiceButtons.find { it.text == correctText }
                ?.setBackgroundColor(getColor(android.R.color.holo_green_light))
        }

        handler.postDelayed({ showNextQuiz() }, 500)
    }

    private fun showResult() {
        quizLayout.visibility = View.GONE
        resultLayout.visibility = View.VISIBLE

        val accuracy = (correctCount.toDouble() / maxQuizCount * 100).toInt()
        resultText.text = "퀴즈 종료!\n점수: $correctCount / $maxQuizCount\n정답률: $accuracy%"

        reviewButton.setOnClickListener {
            val wrongList = wrongQuestions.joinToString("\n") { "${it.word} - ${it.meaning}" }
            Toast.makeText(this, if (wrongList.isNotEmpty()) wrongList else "틀린 문제 없음!", Toast.LENGTH_LONG).show()
        }

        retryButton.setOnClickListener {
            quizCount = 0
            correctCount = 0
            wrongQuestions.clear()
            quizLayout.visibility = View.VISIBLE
            resultLayout.visibility = View.GONE
            showNextQuiz()
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
